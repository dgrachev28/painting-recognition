package com.company.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void recognize(MultipartFile image) throws IOException;
}
