package com.company.recognition.service;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;

public interface MatchService {
    double match(Mat descriptors1, Mat descriptors2);

    void drawMatches(Mat image1, MatOfKeyPoint keypoints1,
                     Mat image2, MatOfKeyPoint keypoints2, MatOfDMatch matches,
                     String resultPath);
}
