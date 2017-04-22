package com.company.core.controller.rpc;

import com.company.core.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void recognize(@RequestParam("image") MultipartFile image) throws IOException {
        imageService.recognize(image);
    }

    @GetMapping
    public void test() {
        System.out.println(1);
    }
}
