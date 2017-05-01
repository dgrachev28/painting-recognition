package com.company.recognition;

import com.company.recognition.service.DescriptorService;
import com.company.recognition.service.MatchService;
import com.company.recognition.service.impl.DescriptorServiceImpl;
import com.company.recognition.service.impl.MatchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DescriptorServiceImpl.class, MatchServiceImpl.class})
public class RecognizerTest {
    @Autowired
    DescriptorService descriptorService;

    @Autowired
    MatchService matchService;

    @Test
    public void test() {
        String imagePath = "/pictures/1(2).jpg";
        String picturesPath = "/pictures";

        List<MatchResult> matchResults = new ArrayList<>();

        Mat templateDescriptors = descriptorService.generateDescriptors(
                descriptorService.getAbsolutePath(imagePath));
        Path dir = Paths.get(descriptorService.getAbsolutePath(picturesPath));

        Map<String, Mat> mats = new HashMap<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                Mat mat = descriptorService.generateDescriptors(file.toAbsolutePath().toString());
                mats.put(file.getFileName().toString(), mat);
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }

        // Increase the number of images 80 times

        Map<String, Mat> mats2 = new HashMap<>();

        for (int i = 0; i < 80; i++) {
            for (String fileName : mats.keySet()) {
                mats2.put(fileName + "sms" + i, mats.get(fileName));
            }
        }

        // matching

        long before = System.currentTimeMillis();
        for (String fileName : mats2.keySet()) {
            MatchResult matchResult = new MatchResult(fileName,
                    matchService.match(templateDescriptors, mats2.get(fileName)));
            matchResults.add(matchResult);
        }
        long after = System.currentTimeMillis();
        System.out.println((after - before) + "ms");

        Collections.sort(matchResults);

        Double distanceSum = -1.0;
        for (MatchResult matchResult : matchResults) {
            if (matchResult.distancesSum != 0.0 && !matchResult.distancesSum.equals(distanceSum)) {
                System.out.println(matchResult.fileName + "\n" + matchResult.distancesSum + "\n" + matchResult.distances + "\n");
                distanceSum = matchResult.distancesSum;
            }
        }
    }

    private class MatchResult implements Comparable {
        String fileName;
        List<Double> distances;
        Double distancesSum;

        public MatchResult(String fileName, List<Double> distances) {
            this.fileName = fileName;
            this.distances = distances;
            this.distancesSum = distances.stream().mapToDouble(d -> d).sum();
        }

        @Override
        public int compareTo(Object o) {
            return (int) Math.round(this.distancesSum - ((MatchResult) o).distancesSum);
        }
    }
}
