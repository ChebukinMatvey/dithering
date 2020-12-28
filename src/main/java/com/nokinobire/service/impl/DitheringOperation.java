package com.nokinobire.service.impl;

import com.nokinobire.service.ImageOperation;
import com.nokinobire.service.JsonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@Service("ditheringOperation")
public class DitheringOperation implements ImageOperation {

    private static final Logger LOG = Logger.getLogger(DitheringOperation.class);

    private List<Color> palette;

    @Resource
    private JsonService jsonService;

    @Override
    public Image process(Image image) {
        BufferedImage bufferedImage = (BufferedImage) image;
        palette = jsonService.readPalette();
        processAlgorithm(bufferedImage);
        return bufferedImage;
    }


    /**
     * for each y from top to bottom do
     * for each x from left to right do
     * oldpixel := pixel[x][y]
     * newpixel := find_closest_palette_color(oldpixel)
     * pixel[x][y] := newpixel
     * quant_error := oldpixel - newpixel
     * pixel[x + 1][y    ] := pixel[x + 1][y    ] + quant_error × 7 / 16
     * pixel[x - 1][y + 1] := pixel[x - 1][y + 1] + quant_error × 3 / 16
     * pixel[x    ][y + 1] := pixel[x    ][y + 1] + quant_error × 5 / 16
     * pixel[x + 1][y + 1] := pixel[x + 1][y + 1] + quant_error × 1 / 16
     * <p>
     * <p>
     * find_closest_palette_color(oldpixel) = round(oldpixel / 256)
     */
    private void processAlgorithm(BufferedImage imageToProcess) {
        int width = imageToProcess.getWidth();
        int height = imageToProcess.getHeight();

        Color oldPixel;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                oldPixel = new Color(imageToProcess.getRGB(j, i));
                processPixel(imageToProcess, oldPixel, j, i);
                spreadErrors(imageToProcess, oldPixel, j, i);
            }
        }
    }

    private void spreadErrors(BufferedImage image, Color oldPixel, int i, int j) {
        Color newPixel = new Color(image.getRGB(i, j));
        int redError = oldPixel.getRed() - newPixel.getRed();
        int greenError = oldPixel.getGreen() - newPixel.getGreen();
        int blueError = oldPixel.getBlue() - newPixel.getBlue();
        spreadErrorToPixel(image, i + 1, j, redError, greenError, blueError, 7 / 16d);
        spreadErrorToPixel(image, i - 1, j + 1, redError, greenError, blueError, 3 / 16d);
        spreadErrorToPixel(image, i, j + 1, redError, greenError, blueError, 5 / 16d);
        spreadErrorToPixel(image, i + 1, j + 1, redError, greenError, blueError, 1 / 16d);
    }

    private void spreadErrorToPixel(BufferedImage image, int i, int j, int redError, int greenError, int blueError, double multiplyValue) {
        if (outOfBounds(image, i, j)) {
            return;
        }
        Color pixelToProcess = new Color(image.getRGB(i, j));
        int newRed = (int) (pixelToProcess.getRed() + redError * multiplyValue);
        int newGreen = (int) (pixelToProcess.getGreen() + greenError * multiplyValue);
        int newBlue = (int) (pixelToProcess.getBlue() + blueError * multiplyValue);
        Color processedPixel = new Color(normalizeColor(newRed), normalizeColor(newGreen), normalizeColor(newBlue));
        image.setRGB(i, j, processedPixel.getRGB());
    }

    private boolean outOfBounds(BufferedImage image, int x, int y) {
        return x < 0 || y < 0 || x > (image.getWidth() - 1) || y > (image.getHeight() - 1);
    }

    private int normalizeColor(int value) {
        if (value < 0) {
            return 0;
        }
        if (value >= 255) {
            return 255;
        }
        return value;
    }

    private void processPixel(BufferedImage image, Color oldPixel, int i, int j) {
        Color newColor = getClosestColor(oldPixel);
        image.setRGB(i, j, newColor.getRGB());
    }

    private Color getClosestColor(Color color) {
        double distance = Double.MAX_VALUE;
        double currentDistance;
        Color closestColor = palette.stream().findFirst().orElse(new Color(0, 0, 0));
        for (Color c : palette) {
            currentDistance = colorDistance(c, color);
            if (currentDistance < distance) {
                closestColor = c;
                distance = currentDistance;
            }
        }
        LOG.debug(String.format("Closest color for [%s]  is [%s]", color, closestColor));
        return closestColor;
    }

    private double colorDistance(Color c1, Color c2) {
        double sum = (Math.pow((c1.getRed() - c2.getRed()), 2)) +
                (Math.pow((c1.getGreen() - c2.getGreen()), 2)) +
                (Math.pow((c1.getBlue() - c2.getBlue()), 2));
        double distance = Math.sqrt(sum);
        LOG.debug(String.format("Color distance [%s] to [%s] = %4.3f", c1, c2, distance));
        return distance;
    }
}
