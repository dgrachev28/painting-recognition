package com.company.recognition.converter;

import com.google.gson.Gson;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DescriptorConverter {
    private static final Logger log = LoggerFactory.getLogger(DescriptorConverter.class);

    private static final String ROWS_PROPERTY = "rows";
    private static final String COLS_PROPERTY = "cols";
    private static final String TYPE_PROPERTY = "type";
    private static final String DATA_PROPERTY = "data";

    /**
     * Code from http://answers.opencv.org/question/8873/best-way-to-store-a-mat-object-in-android/?answer=28608#post-id-28608
     */
    public static String matToJson(Mat mat) {
        Map<String, String> matProperties = new HashMap<>();
        if (mat.isContinuous()) {
            matProperties.put(COLS_PROPERTY, String.valueOf(mat.cols()));
            matProperties.put(ROWS_PROPERTY, String.valueOf(mat.rows()));
            matProperties.put(TYPE_PROPERTY, String.valueOf(mat.type()));
            byte[] data = new byte[mat.cols() * mat.rows() * (int) mat.elemSize()];
            mat.get(0, 0, data);
            // We cannot set binary data to a json object, so encoding data byte array to Base64.
            matProperties.put(DATA_PROPERTY, new String(Base64.getEncoder().encode(data)));
            return new Gson().toJson(matProperties);
        } else {
            log.error("Mat is not continuous");
        }
        return null;
    }

    public static Mat matFromJson(String json) {
        HashMap matProperties = new Gson().fromJson(json, HashMap.class);
        int rows = new Integer((String) matProperties.get(ROWS_PROPERTY));
        int cols = new Integer((String) matProperties.get(COLS_PROPERTY));
        int type = new Integer((String) matProperties.get(TYPE_PROPERTY));
        byte[] data = Base64.getDecoder().decode((String) matProperties.get(DATA_PROPERTY));
        Mat mat = new Mat(rows, cols, type);
        mat.put(0, 0, data);
        return mat;
    }
}
