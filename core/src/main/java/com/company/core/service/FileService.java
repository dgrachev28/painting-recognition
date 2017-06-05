package com.company.core.service;

import com.company.core.entity.Picture;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FileService {

    String saveImageFile(String imageUrl, Picture picture) throws IOException;

    String getAbsoluteImagePath(String relativePath);

}
