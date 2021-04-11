package com.nokinobire.service;

import java.awt.*;
import java.util.List;

/**
 * Service to do operations with colors
 */
public interface ColorsService {


    /**
     * Get closest color (simple vector distance implementation)
     *
     * @param color   - to what color ?
     * @param palette - to what palette ?
     * @return - return closest one otherwise (if palette is empty new color 0 0 0 )
     */
    Color getClosestColor(Color color, List<Color> palette);

}
