package com.nokinobire.service;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public interface LoadService {

    Image read(File file) throws IOException;

    void write(Image image, File file) throws IOException;

}
