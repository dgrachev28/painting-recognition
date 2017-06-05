package com.company.core;

import com.company.core.entity.Picture;
import com.company.core.repository.PictureRepository;
import com.company.core.service.GenerateDescriptorsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class GenerateDescriptorsServiceTest {

    @Autowired
    private GenerateDescriptorsService generateDescriptorsService;

    @Autowired
    private PictureRepository pictureRepository;

    @Test
    public void generateForOne() {
        Picture picture = pictureRepository.findOne(65L);
        generateDescriptorsService.generateOne(picture);
    }

    @Test
    public void generateAll() {
        generateDescriptorsService.generateAll();
    }
}
