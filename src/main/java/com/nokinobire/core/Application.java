package com.nokinobire.core;

import com.nokinobire.core.exceptions.NotImplementedException;
import com.nokinobire.service.ImageOperation;
import com.nokinobire.service.JsonService;
import com.nokinobire.service.LoadService;
import com.nokinobire.service.ReplaceColorsService;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.OperationNotSupportedException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class Application implements Runnable {

    private static final Logger LOG = Logger.getLogger(Application.class);


    @Value("${file.path.in}")
    private String inputPath;
    @Value("${file.path.out}")
    private String outputPath;

    @Resource
    private LoadService loadService;
    @Resource
    private JsonService jsonService;
    @Resource
    private ImageOperation ditheringOperation;
    @Resource
    private ImageOperation replaceColorsService;

    @Override
    public void run() {
        try {
            Image image = loadService.read(new File(inputPath));
            List<Color> palette = jsonService.readPalette();
            Image processedImage = replaceColorsService.process(image, palette);
            File out = new File(outputPath);
            out.createNewFile();
            loadService.write(processedImage, out);
        } catch (IOException | NotImplementedException e) {
            LOG.error(e);
        }
    }

}
