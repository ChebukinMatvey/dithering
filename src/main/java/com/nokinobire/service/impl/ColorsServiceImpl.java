package com.nokinobire.service.impl;

import com.nokinobire.service.ColorsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.List;

@Service
public class ColorsServiceImpl implements ColorsService {

    private static final Logger LOG = Logger.getLogger(ColorsServiceImpl.class);


    @Override
    public Color getClosestColor(Color color, List<Color> palette) {
        if (CollectionUtils.isEmpty(palette)) {
            return new Color(0, 0, 0);
        }
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
