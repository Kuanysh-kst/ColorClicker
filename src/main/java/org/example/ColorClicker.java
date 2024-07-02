package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class ColorClicker extends JPanel {
    private static final Color GREEN_FLOWER = new Color(46, 161, 0); // Это точный
    private static final Color GRAY_BOMB = new Color(128, 128, 128); // Бурда нужно заментьЗамените на точный цвет

    //также нужно запелить на синий кристал клик, и отступы от серых бомб
    private static final Rectangle WORK_AREA = new Rectangle(100, 100, 800, 600);
    private BufferedImage screenCapture;

    public ColorClicker() {
        Timer timer = new Timer(100, e -> {
            try {
                Robot robot = new Robot();
                screenCapture = robot.createScreenCapture(WORK_AREA);
                for (int x = 0; x < screenCapture.getWidth(); x++) {
                    for (int y = 0; y < screenCapture.getHeight(); y++) {
                        Color color = new Color(screenCapture.getRGB(x, y));
                        if (isGreenFlower(color) && !isGrayBombNearby(screenCapture, x, y)) {
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
        timer.start();
    }

    private boolean isGreenFlower(Color color) {
        return color.equals(GREEN_FLOWER);
    }

    private boolean isGrayBombNearby(BufferedImage image, int x, int y) {
        int radius = 5;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (x + dx >= 0 && x + dx < image.getWidth() && y + dy >= 0 && y + dy < image.getHeight()) {
                    Color nearbyColor = new Color(image.getRGB(x + dx, y + dy));
                    if (nearbyColor.equals(GRAY_BOMB)) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Color Clicker");
            ColorClicker colorClicker = new ColorClicker();
            frame.add(colorClicker);
            frame.setSize(WORK_AREA.width/2 + WORK_AREA.x, WORK_AREA.height + WORK_AREA.y);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
