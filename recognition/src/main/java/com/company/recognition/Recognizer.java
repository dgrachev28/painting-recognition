package com.company.recognition;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Recognizer {

    static {
        OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }


    public void recognize() {
        // got code from here: http://stackoverflow.com/questions/24569386/opencv-filtering-orb-matches
        Mat image = Imgcodecs.imread(getClass().getResource("/picture_perov.jpg").getPath());

        FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
//        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        // First photo
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
        Mat descriptors = new Mat();
        MatOfKeyPoint keypoints = new MatOfKeyPoint();

        detector.detect(image, keypoints);
        descriptor.compute(image, keypoints, descriptors);
    }
}
