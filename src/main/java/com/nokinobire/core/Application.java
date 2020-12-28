package com.nokinobire.core;

import com.nokinobire.service.ImageOperation;
import com.nokinobire.service.LoadService;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Service
public class Application implements Runnable {

    private static final Logger LOG = Logger.getLogger(Application.class);


    @Value("${file.path.in}")
    private String inputPath;
    @Value("${file.path.out}")
    private String outputPath ;

    @Resource
    private LoadService loadService;
    @Resource
    private ImageOperation ditheringOperation;

    @Override
    public void run() {
        try {
            Image image = loadService.read(new File(inputPath));
            Image processedImage = ditheringOperation.process(image);
            File out = new File(outputPath);
            out.createNewFile();
            loadService.write(processedImage, out);
        } catch (IOException | ParseException e) {
            LOG.error(e);
        }
    }

}
