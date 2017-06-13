package com.company.core.dto;

import lombok.Data;

@Data
public class PictureDto {

    private String title;
    private String description;
    private String year;
    private String shortInfo;
    private String gallery;
    private String imageBase64;
}
