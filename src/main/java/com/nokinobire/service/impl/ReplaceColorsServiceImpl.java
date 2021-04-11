package com.nokinobire.service.impl;


import com.nokinobire.service.ColorsService;
import com.nokinobire.service.ImageOperation;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ReplaceColorsServiceImpl implements ImageOperation {

    private static final Logger LOG = Logger.getLogger(ReplaceColorsServiceImpl.class);

    @Resource
    private ColorsService colorsService;

    @Override
    public Image process(Image image, List<Color> palette) {
        BufferedImage processedImage = (BufferedImage) image;
        processAlgorithm(processedImage, palette);
        return processedImage;
    }

    private void processAlgorithm(BufferedImage image, List<Color> palette) {
        int height = image.getHeight();
        int width = image.getWidth();

        Color oldPixel;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                oldPixel = new Color(image.getRGB(j, i));
                Color closest = colorsService.getClosestColor(oldPixel, palette);
                image.getRaster().setPixel(j, i, new int[]{closest.getRed(), closest.getGreen(), closest.getBlue()});
            }
        }
    }

}
