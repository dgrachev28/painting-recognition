package com.company.core.service;

import com.company.core.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Picture recognize(MultipartFile image) throws IOException;
}
