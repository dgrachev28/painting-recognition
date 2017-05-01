package com.company.recognition.service.impl;

import com.company.recognition.service.DescriptorService;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DescriptorServiceImpl implements DescriptorService {
    /**
     * @param nfeatures
     * @param scaleFactor
     * @param nlevels
     * @param edgeThreshold
     * @param firstLevel
     * @param WTA_K
     * @param scoreType
     * @param patchSize
     * @param fastThreshold
     *
     * http://docs.opencv.org/trunk/d1/d89/tutorial_py_orb.html
     *
     * default arguments: 500, 1.2f, 8, 31, 0, 2, ORB.HARRIS_SCORE, 31, 20
     */
    private final ORB orb = ORB.create(100, 1.2f, 32, 31, 0, 2, ORB.HARRIS_SCORE, 31, 20);

    static {
        OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public Mat generateDescriptors(String path) {
        Mat image = Imgcodecs.imread(path);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
        Mat descriptors = new Mat();
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        orb.detect(image, keypoints);
        orb.compute(image, keypoints, descriptors);
        return descriptors;
    }


    // TODO remove this method
    public String getAbsolutePath(String path) {
        String imagePath = getClass().getResource(path).getFile();
        File imageFile = new File(imagePath);
        return imageFile.getAbsolutePath();
    }
}
