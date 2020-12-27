package com.nokinobire.service;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public interface ImageOperation {

    /**
     * Process image than return new one
     */
    Image process(Image image) throws IOException, ParseException;

}
