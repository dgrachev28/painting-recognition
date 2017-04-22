package com.company.core.service.impl;

import com.company.core.AppProperties;
import com.company.core.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private AppProperties appProperties;

    public ImageServiceImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public void recognize(MultipartFile image) throws IOException {
        saveMultipartFileToFile(image);
    }

    private void saveMultipartFileToFile(MultipartFile multipart) throws IOException {
        File file = new File(appProperties.getTmpFolder() + "/abc.txt");
        multipart.transferTo(file);
    }

}
