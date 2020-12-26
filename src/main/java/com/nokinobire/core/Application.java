package com.nokinobire.core;

import com.nokinobire.service.LoadService;
import org.apache.log4j.Logger;
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

    @Override
    public void run() {
        try {
            Image image = loadService.read(new File(INPUT));
            File outFile = new File(OUTPUT);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            loadService.write(image, outFile);
        } catch (IOException e) {
            LOG.error(e);
        }
    }

}
