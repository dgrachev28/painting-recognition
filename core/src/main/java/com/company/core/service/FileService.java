package com.company.core.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FileService {

    String saveImageFile(String imageUrl, long pictureId) throws IOException;

    String getAbsoluteImagePath(String relativePath);

}
