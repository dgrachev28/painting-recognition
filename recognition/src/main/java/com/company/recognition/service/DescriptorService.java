package com.company.recognition.service;

import org.opencv.core.Mat;

public interface DescriptorService {
    Mat generateDescriptors(String path);

    String getAbsolutePath(String path);
}
