package com.company.core.service;

import com.company.core.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageRecognitionService {

    Picture recognize(MultipartFile image) throws IOException;
}
