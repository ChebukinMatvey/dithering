package com.nokinobire.core;

import com.nokinobire.service.ImageOperation;
import com.nokinobire.service.LoadService;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Service
public class Application implements Runnable {

    private static final Logger LOG = Logger.getLogger(Application.class);
    private static final String INPUT = "./images/in.jpg";
    private static final String OUTPUT = "./images/out.jpg";

    @Resource
    private LoadService loadService;
    @Resource
    private ImageOperation ditheringOperation;

    @Override
    public void run() {
        try {
            Image image = loadService.read(new File(INPUT));
            ditheringOperation.process(image);
            loadService.write(image, new File(INPUT));
        } catch (IOException | ParseException e) {
            LOG.error(e);
        }
    }

}
