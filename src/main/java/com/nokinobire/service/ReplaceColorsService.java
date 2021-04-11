package com.nokinobire.service;


import java.awt.*;
import java.util.List;

/**
 * Service to replace image colors by specific palette
 */
public interface ReplaceColorsService {


    /**
     * By detecting color distance ( simple vector distance )
     * choose nearest one and replace existing
     *
     * @param toProcess image to process
     * @param palette   colors to replace exisitng ones
     * @return
     */
    Image replaceColors(Image toProcess, List<Color> palette);


}
