package com.company.core.service.impl;

import com.company.core.converter.PictureConverter;
import com.company.core.dto.PictureDto;
import com.company.core.entity.Picture;
import com.company.core.service.FileService;
import com.company.core.service.ImageRecognitionService;
import com.company.core.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private ImageRecognitionService imageRecognitionService;

    @Autowired
    private FileService fileService;

    @Override
    public PictureDto recognize(MultipartFile image) {
        try {
            Picture picture = imageRecognitionService.recognize(image);
            File file = new File(fileService.getAbsoluteImagePath(picture.getImagePath()));
            String imageBase64 = new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(file)));
            return PictureConverter.toDto(picture, imageBase64);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}