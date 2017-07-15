package com.company.core.controller;

import com.company.core.dto.PictureDto;
import com.company.core.service.PictureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/recognize")
    public PictureDto recognize(@RequestParam("image") MultipartFile image) throws IOException {
        return pictureService.recognize(image);
    }
}