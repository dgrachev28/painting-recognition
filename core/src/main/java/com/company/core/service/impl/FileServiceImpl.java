package com.company.core.service.impl;


import com.company.core.AppProperties;
import com.company.core.entity.Picture;
import com.company.core.service.FileService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class FileServiceImpl implements FileService {

    private final AppProperties appProperties;

    public FileServiceImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public String saveImageFile(String imageUrl, Picture picture) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);
        String imagePath = "/" + picture.getId() + ".jpg";
        ImageIO.write(image, "jpg", new File(getAbsoluteImagePath(imagePath)));
        return imagePath;
    }

    @Override
    public String getAbsoluteImagePath(String relativePath) {
        return appProperties.getImageFolder() + relativePath;
    }

}
