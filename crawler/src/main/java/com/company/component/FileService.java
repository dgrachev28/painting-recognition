package com.company.component;

import com.company.core.AppProperties;
import com.company.core.entity.Picture;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class FileService {

    private final AppProperties appProperties;

    public FileService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String saveImageFile(String imageUrl, Picture picture) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);
        String imagePath = appProperties.getImageFolder() + "/" + picture.getId() + ".jpg";
        ImageIO.write(image, "jpg", new File(imagePath));
        return imagePath;
    }

}
