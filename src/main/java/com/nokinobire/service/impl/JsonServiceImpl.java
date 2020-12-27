package com.nokinobire.service.impl;

import com.nokinobire.service.JsonService;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service("jsonService")
public class JsonServiceImpl implements JsonService {

    private static final Logger LOG = Logger.getLogger(JsonServiceImpl.class);

    // todo: move to properties file
    private static final String PALETTE_FILE = "./src/main/resources/palette.json";

    // todo: remove cast to list
    @Override
    public List<Color> readPalette() {
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            String json = readFileContent(PALETTE_FILE);
            obj = (JSONObject) parser.parse(json);
        } catch (ParseException | FileNotFoundException e) {
            LOG.error("Error while trying to read colors from json file", e);
            throw new IllegalStateException("Cant read colors from json file", e);
        }
        return createColors((JSONArray) obj.get("colors"));
    }

    private List<Color> createColors(JSONArray array) {
        if (CollectionUtils.isEmpty(array)) {
            return Collections.emptyList();
        }
        return (List<Color>) array.stream()
                .map(this::createColor)
                .collect(Collectors.toList());
    }

    private Color createColor(Object o) {
        JSONObject obj = (JSONObject) o;
        int red = ((Long) obj.get("red")).intValue();
        int green = ((Long) obj.get("green")).intValue();
        int blue = ((Long) obj.get("blue")).intValue();
        return new Color(red, green, blue);
    }


    private String readFileContent(String filePath) throws FileNotFoundException {
        String content;
        try (Scanner scanner = new Scanner(new File(filePath))) {
            content = scanner.useDelimiter("\\Z").next();
        }
        return content;
    }

}
