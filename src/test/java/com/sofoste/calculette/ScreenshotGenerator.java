package com.sofoste.calculette;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/** Generates the README image from the real Swing interface. */
public final class ScreenshotGenerator {
    private ScreenshotGenerator() {}

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                CalculetteApp.CalculatorFrame frame = new CalculetteApp.CalculatorFrame();
                frame.setLocation(-10_000, -10_000);
                frame.setVisible(true);
                click(frame, "1"); click(frame, "2"); click(frame, "×"); click(frame, "3"); click(frame, "=");
                Container content = frame.getContentPane();
                BufferedImage image = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = image.createGraphics();
                content.printAll(graphics);
                graphics.dispose();
                ImageIO.write(image, "png", new File(args[0]));
                frame.dispose();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    private static void click(Container container, String label) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton button && label.equals(button.getText())) {
                button.doClick();
                return;
            }
            if (component instanceof Container child) click(child, label);
        }
    }
}
