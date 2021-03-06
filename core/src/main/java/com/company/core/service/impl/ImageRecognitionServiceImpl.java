package com.company.core.service.impl;

import com.company.core.AppProperties;
import com.company.core.entity.Picture;
import com.company.core.entity.PictureDescriptor;
import com.company.core.repository.PictureDescriptorRepository;
import com.company.core.service.ImageRecognitionService;
import com.company.recognition.converter.DescriptorConverter;
import com.company.recognition.service.DescriptorService;
import com.company.recognition.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ImageRecognitionServiceImpl implements ImageRecognitionService {

    private final AtomicLong pictureNumber = new AtomicLong(0L);

    private final AppProperties appProperties;
    private final DescriptorService descriptorService;
    private final MatchService matchService;
    private final PictureDescriptorRepository pictureDescriptorRepository;


    @Override
    public Picture recognize(MultipartFile image) throws IOException {
        String path = saveMultipartFileToFile(image);
        Mat descriptor = descriptorService.generateDescriptors(path);
        List<PictureDescriptor> pictureDescriptors = pictureDescriptorRepository.findAll();
        double minDistance = Double.MAX_VALUE;
        Picture picture = null;
        for (PictureDescriptor pictureDescriptor : pictureDescriptors) {
            double distance = matchService.match(
                    DescriptorConverter.jsonToMat(pictureDescriptor.getDescriptor()),
                    descriptor);
            if (distance < minDistance) {
                minDistance = distance;
                picture = pictureDescriptor.getPicture();
            }
        }
        return picture;
    }

    private String saveMultipartFileToFile(MultipartFile multipart) throws IOException {
        File file = new File(appProperties.getTmpFolder() + generatePictureFileName());
        multipart.transferTo(file);
        return file.getAbsolutePath();
    }

    private String generatePictureFileName() {
        return "/picture" + pictureNumber.incrementAndGet() + "." + appProperties.getImageExtension();
    }

}
