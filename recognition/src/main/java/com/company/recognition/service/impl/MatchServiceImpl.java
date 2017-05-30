package com.company.recognition.service.impl;

import com.company.recognition.service.MatchService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {
    private final DescriptorMatcher MATCHER = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

    public double match(Mat descriptors1, Mat descriptors2) {
        MatOfDMatch matches = new MatOfDMatch();
        MATCHER.match(descriptors1, descriptors2, matches);
//        return matches.toList().stream()
//                .sorted((a, b) -> Math.round(a.distance - b.distance))
//                .limit(10)
//                .map(e -> (double) e.distance)
//                .collect(Collectors.toList());
        return matches.toList().stream()
                .mapToDouble(e -> (double) e.distance)
                .sorted()
                .limit(10)
                .sum();
    }

    public void drawMatches(Mat image1, MatOfKeyPoint keypoints1,
                             Mat image2, MatOfKeyPoint keypoints2, MatOfDMatch matches,
                             String resultPath) {
        Scalar RED = new Scalar(255, 0, 0);
        Scalar GREEN = new Scalar(0, 255, 0);

        System.out.println(matches.size() + " " + matches.size());

        Mat outputImg = new Mat();
        MatOfByte drawnMatches = new MatOfByte();
        Features2d.drawMatches(image1, keypoints1, image2, keypoints2,
                matches, outputImg, GREEN, RED, drawnMatches, Features2d.NOT_DRAW_SINGLE_POINTS);

        Imgcodecs.imwrite(resultPath, outputImg);
    }
}
