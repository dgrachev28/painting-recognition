package com.company.core.service.impl;

import com.company.core.entity.Picture;
import com.company.core.entity.PictureDescriptor;
import com.company.core.repository.PictureDescriptorRepository;
import com.company.core.repository.PictureRepository;
import com.company.core.service.FileService;
import com.company.core.service.GenerateDescriptorsService;
import com.company.recognition.converter.DescriptorConverter;
import com.company.recognition.service.DescriptorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenerateDescriptorsServiceImpl implements GenerateDescriptorsService {

    private final DescriptorService descriptorService;
    private final PictureRepository pictureRepository;
    private final PictureDescriptorRepository pictureDescriptorRepository;
    private final FileService fileService;


    @Override
    public void generateOne(Picture picture) {
        PictureDescriptor descriptor = pictureDescriptorRepository.findOneByPicture(picture);
        if (descriptor == null) {
            descriptor = new PictureDescriptor();
            descriptor.setPicture(picture);
            String absolutePath = fileService.getAbsoluteImagePath(picture.getImagePath());
            String mat = DescriptorConverter.matToJson(descriptorService.generateDescriptors(absolutePath));
            descriptor.setDescriptor(mat);
            pictureDescriptorRepository.save(descriptor);
        }
    }

    @Override
    public void generateAll() {
        List<Picture> pictures = pictureRepository.findAll();
        for (Picture picture : pictures) {
            generateOne(picture);
        }
    }
}
