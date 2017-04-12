package com.company.recognition;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Recognizer {
    private final FeatureDetector DETECTOR = FeatureDetector.create(FeatureDetector.ORB);
    private final DescriptorExtractor DESCRIPTOR = DescriptorExtractor.create(DescriptorExtractor.ORB);
    private final DescriptorMatcher MATCHER = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

    private final String PICTURES_PATH = "/pictures";


    static {
        OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    private String getAbsolutePath(String path) {
        String imagePath = getClass().getResource(path).getFile();
        File imageFile = new File(imagePath);
        return imageFile.getAbsolutePath();
    }

    /*
     * path: absolute path to the image
     */
    private Mat getDescriptors(String path) {
        Mat image = Imgcodecs.imread(path);

        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
        Mat descriptors = new Mat();
        MatOfKeyPoint keypoints = new MatOfKeyPoint();

        DETECTOR.detect(image, keypoints);
        DESCRIPTOR.compute(image, keypoints, descriptors);
        return descriptors;
    }

    private void drawMatches(Mat image1, MatOfKeyPoint keypoints1,
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

    private Double matchResult(Mat descriptors1, Mat descriptors2) {
        MatOfDMatch matches = new MatOfDMatch();
        MATCHER.match(descriptors1, descriptors2, matches);
        return matches.toList().stream()
                .sorted((a, b) -> Math.round(a.distance - b.distance))
                .limit(20)
                .mapToDouble(e -> e.distance)
                .peek(e -> System.out.print(e + " "))
                .sum();
    }


    public void recognize(String imagePath) {
        // got code from here: http://stackoverflow.com/questions/24569386/opencv-filtering-orb-matches

        Mat templateDescriptors = getDescriptors(getAbsolutePath(imagePath));
        Path dir = Paths.get(getAbsolutePath(PICTURES_PATH));
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
                Mat additionalDescriptors = getDescriptors(file.toAbsolutePath().toString());
                System.out.println("\n" + matchResult(templateDescriptors, additionalDescriptors) + "\n");
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
    }
}
