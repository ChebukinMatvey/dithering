package com.nokinobire.service;

import com.nokinobire.core.exceptions.NotImplementedException;
import org.json.simple.parser.ParseException;

import javax.naming.OperationNotSupportedException;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public interface ImageOperation {

    /**
     * Process image than return new one
     */
    default Image process(Image image) {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * Do operation which needs color palette it it
     *
     * @param image
     * @param colors
     * @return
     */
    default Image process(Image image, List<Color> colors) {
        throw new NotImplementedException("Not implemented");
    }

}
