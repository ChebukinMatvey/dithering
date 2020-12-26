package com.nokinobire.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;


@Service
public interface LoadService {

    Image read(File file) throws IOException;

    void write(Image image, File file) throws IOException;

}
