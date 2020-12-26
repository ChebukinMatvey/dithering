package com.nokinobire.service.impl;


import com.nokinobire.service.LoadService;
import com.nokinobire.core.ValidationUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

@Service("loadService")
public class LoadServiceImpl implements LoadService {

    private static final String IMAGE_FORMAT = "jpg";
    private static final Logger LOG = Logger.getLogger(LoadServiceImpl.class);


    @Override
    public Image read(File file) throws IOException {
        ValidationUtils.validateNotNull(file);
        ValidationUtils.validateFileExist(file);
        LOG.debug(String.format("Trying to read image from file with name [ %s ]", file.getName()));
        Image resultImage = ImageIO.read(file);
        LOG.debug("Done");
        return resultImage;
    }

    @Override
    public void write(Image image, File file) throws IOException {
        ValidationUtils.validateNotNull(image, file);
        ValidationUtils.validateFileExist(file);
        LOG.debug(String.format("Trying to write image into file with name [ %s ]", file.getName()));
        ImageIO.write((RenderedImage) image, IMAGE_FORMAT, file);
        LOG.debug("Done");
    }

}
