package com.company.core.controller.rpc;

import com.company.core.dto.PictureDto;
import com.company.core.service.ImageService;
import com.sun.org.apache.xml.internal.security.utils.Base64;
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
        PictureDto pictureDto = new PictureDto();
        pictureDto.setTitle("title");
        pictureDto.setImageBase64(Base64.encode(image.getBytes()));
        imageService.recognize(image);
        return pictureDto;
    }
}
