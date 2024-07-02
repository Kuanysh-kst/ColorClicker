package org.example;

import java.awt.*;
import java.awt.event.InputEvent;

public class Main {
    public static void main(String[] args) throws AWTException {
        try {
            while (true) {
                Robot r = new Robot();
                int button = InputEvent.BUTTON1_DOWN_MASK;
                System.out.println("Clicks");
                r.mousePress(button);
                Thread.sleep(400);
                r.mouseRelease(button);
                Thread.sleep(2000);
            }
        }catch (AWTException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}