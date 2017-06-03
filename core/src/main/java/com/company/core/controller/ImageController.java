package com.company.core.controller;

import com.company.core.converter.PictureConverter;
import com.company.core.dto.PictureDto;
import com.company.core.entity.Picture;
import com.company.core.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/recognize")
    public PictureDto recognize(@RequestParam("image") MultipartFile image) throws IOException {
        Picture picture = imageService.recognize(image);
        return PictureConverter.toDto(picture);
    }
}
