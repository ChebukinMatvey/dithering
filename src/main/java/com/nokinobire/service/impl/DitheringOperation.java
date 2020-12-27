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

    // todo: create palette file path property
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
                processPixel(imageToProcess, oldPixel, i, j);
                spreadError(imageToProcess, oldPixel, i, j);
            }
        }
    }

    private void spreadError(BufferedImage imageToProcess, Color oldPixel, int i, int j) {
        // todo: implement
    }

    private void processPixel(BufferedImage image, Color oldPixel, int i, int j) {
        Color newColor = getClosestColor(oldPixel);
        image.setRGB(j, i, newColor.getRGB());
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
        return closestColor;
    }

    private double colorDistance(Color c1, Color c2) {
        double sum = (Math.pow((c1.getRed() - c2.getRed()), 2)) +
                (Math.pow((c1.getGreen() - c2.getGreen()), 2)) +
                (Math.pow((c1.getBlue() - c2.getBlue()), 2));
        double distance = Math.sqrt(sum);
        LOG.debug(String.format("Color distance [%s] to [%s] = %4.3f", c1.toString(), c2.toString(), distance));
        return distance;
    }
}
