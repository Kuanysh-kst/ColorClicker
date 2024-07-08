package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
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
    private final JButton startButton;
    private final JButton stopButton;
    private final JButton broadcastButton;
    private boolean running = false;

    public ColorClicker() {
        setLayout(new BorderLayout());

        startButton = new JButton("Старт");
        broadcastButton = new JButton("Трансляция");
        startButton.addActionListener(e -> {
            startClicking();
            broadcastButton.setEnabled(false); // Отключаем кнопку трансляции при старте
        });

        stopButton = new JButton("Стоп");
        stopButton.setEnabled(false); // Изначально делаем кнопку стоп неактивной
        stopButton.addActionListener(e -> {
            stopClicking();
            broadcastButton.setEnabled(true); // Включаем кнопку трансляции при остановке
        });


        broadcastButton.setEnabled(false); // Изначально делаем кнопку трансляции неактивной
        broadcastButton.addActionListener(e -> {
            waiting();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            broadcastButton.setEnabled(false);
            // Перерисовываем панель для обновления отображаемой области

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(broadcastButton);
        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(50, e -> {
            try {
                if (running) {
                    Robot robot = new Robot();
                    screenCapture = robot.createScreenCapture(WORK_AREA);
                    for (int x = 0; x < screenCapture.getWidth(); x++) {
                        for (int y = 0; y < screenCapture.getHeight(); y++) {
                            Color color = new Color(screenCapture.getRGB(x, y));
                            if (ColorChecker.itsVersion1(color)) {
                                int absoluteX = x + WORK_AREA.x;
                                int absoluteY = y + WORK_AREA.y;
                                robot.mouseMove(absoluteX, absoluteY);
                                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            }
                        }
                    }
                    repaint(); // Перерисовываем панель для обновления отображаемой области
                }else {
                    Robot robot = new Robot();
                    screenCapture = robot.createScreenCapture(WORK_AREA);
                    repaint(); // Перерисовываем панель для обновления отображаемой области
                }
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        });
        timer.start();

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
    }

    private void startClicking() {
        timer.start();
        startButton.setEnabled(false); // Делаем кнопку старт неактивной при запуске
        stopButton.setEnabled(true); // Включаем кнопку стоп при запуске
        running = true;
    }

    private void stopClicking() {
        timer.stop();
        startButton.setEnabled(true); // Включаем кнопку старт при остановке
        stopButton.setEnabled(false); // Делаем кнопку стоп неактивной при остановке
        running = false;
    }

    private void waiting() {
        timer.start();
        startButton.setEnabled(true); // Включаем кнопку старт при остановке
        stopButton.setEnabled(false); // Делаем кнопку стоп неактивной при остановке
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
        } else if (e.getKeyCode() == NativeKeyEvent.VC_TAB) {
            stopClicking();
            broadcastButton.setEnabled(true);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Color Clicker");
            ColorClicker colorClicker = new ColorClicker();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());

            int taskBarHeight = screenInsets.bottom;
            int windowWidth = screenSize.width / 2;
            int windowHeight = screenSize.height - taskBarHeight;
            int x = 0;
            int y = 0;

            frame.add(colorClicker);
            frame.setSize(windowWidth, windowHeight);
            frame.setLocation(x, y);

            // Устанавливаем рабочую область по центру окна
            int workAreaWidth = 350; // Ширина зеленой рабочей области
            int workAreaHeight = 480; // Высота зеленой рабочей области
            int workAreaX = (screenSize.width / 2) + (windowWidth - workAreaWidth) / 2;
            int workAreaY = (windowHeight - workAreaHeight) / 2;
            WORK_AREA = new Rectangle(workAreaX, workAreaY, workAreaWidth, workAreaHeight);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}