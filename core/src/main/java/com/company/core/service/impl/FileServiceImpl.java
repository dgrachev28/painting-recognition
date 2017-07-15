package com.company.core.service.impl;


import com.company.core.AppProperties;
import com.company.core.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final AppProperties appProperties;

    @Override
    public String saveImageFile(String imageUrl, long pictureId) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);
        String imagePath = "/" + pictureId + ".jpg";
        ImageIO.write(image, "jpg", new File(getAbsoluteImagePath(imagePath)));
        return imagePath;
    }

    @Override
    public String getAbsoluteImagePath(String relativePath) {
        return appProperties.getImageFolder() + relativePath;
    }

}
