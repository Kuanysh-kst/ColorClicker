package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;


public class ColorClicker extends JPanel implements NativeKeyListener {
    private static Rectangle WORK_AREA;
    private BufferedImage screenCapture;
    private final Timer timer;
    private final JButton startStopButton;
    private boolean running = false;

    public ColorClicker() {
        setLayout(new BorderLayout());

        startStopButton = new JButton("Старт");
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (running) {
                    stopClicking();
                } else {
                    startClicking();
                }
            }
        });

        add(startStopButton, BorderLayout.SOUTH);

        timer = new Timer(10, e -> {
            try {
                Robot robot = new Robot();
                screenCapture = robot.createScreenCapture(WORK_AREA);
                for (int x = 0; x < screenCapture.getWidth(); x++) {
                    for (int y = 0; y < screenCapture.getHeight(); y++) {
                        Color color = new Color(screenCapture.getRGB(x, y));
                        if (ColorChecker.itsVersion2(color)) {
                            int absoluteX = x + WORK_AREA.x;
                            int absoluteY = y + WORK_AREA.y;
                            robot.mouseMove(absoluteX, absoluteY);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        }
                    }
                }
                repaint();
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        });

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void startClicking() {
        timer.start();
        startStopButton.setText("Стоп");
        running = true;
    }

    private void stopClicking() {
        timer.stop();
        startStopButton.setText("Старт");
        running = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (screenCapture != null) {
            g.drawImage(screenCapture, 0, 0, this);
            g.setColor(new Color(0, 255, 0, 128)); // Полупрозрачный зеленый цвет
            g.fillRect(0, 0, WORK_AREA.width, WORK_AREA.height);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Не требуется реализовывать
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Не требуется реализовывать
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Color Clicker");
            ColorClicker colorClicker = new ColorClicker();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());

            int taskBarHeight = screenInsets.bottom;
            int windowWidth = screenSize.width / 2;
            int windowHeight = screenSize.height - taskBarHeight;
            int x = screenSize.width / 2;
            int y = 0;

            frame.add(colorClicker);
            frame.setSize(windowWidth, windowHeight);
            frame.setLocation(x, y);

            // Устанавливаем рабочую область с левой стороны окна до середины по горизонтали
            WORK_AREA = new Rectangle(0, 0, windowWidth, windowHeight);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}