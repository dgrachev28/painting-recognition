package com.company.recognition;

import com.company.recognition.service.DescriptorService;
import com.company.recognition.converter.DescriptorConverter;
import com.company.recognition.service.impl.DescriptorServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DescriptorServiceImpl.class, DescriptorConverter.class})
public class DescriptorConverterTest {
    @Autowired
    DescriptorService descriptorService;

    @Test
    public void test() {
        String imagePath = "/converter/picture.jpg";
        Mat mat = descriptorService.generateDescriptors(descriptorService.getAbsolutePath(imagePath));
        String json = DescriptorConverter.matToJson(mat);
        Mat newMat = DescriptorConverter.jsonToMat(json);

        Assert.assertEquals(mat.cols(), newMat.cols());
        Assert.assertEquals(mat.rows(), newMat.rows());
        Assert.assertEquals(mat.type(), newMat.type());

        byte[] data = new byte[mat.cols() * mat.rows() * (int) mat.elemSize()];
        mat.get(0, 0, data);
        byte[] newData = new byte[newMat.cols() * newMat.rows() * (int) newMat.elemSize()];
        newMat.get(0, 0, newData);
        Assert.assertArrayEquals(data, newData);
    }
}
