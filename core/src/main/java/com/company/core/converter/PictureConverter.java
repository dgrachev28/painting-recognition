package com.company.core.converter;

import com.company.core.dto.PictureDto;
import com.company.core.entity.Picture;

import java.io.IOException;

public class PictureConverter {
    public static PictureDto toDto(Picture picture, String imageBase64) throws IOException {
        if (picture == null) {
            return null;
        }
        PictureDto pictureDto = new PictureDto();
        pictureDto.setDescription(picture.getDescription());
        pictureDto.setGallery(picture.getGallery());
        pictureDto.setShortInfo(picture.getShortInfo());
        pictureDto.setTitle(picture.getTitle());
        pictureDto.setYear(picture.getYear());
        pictureDto.setImageBase64(imageBase64);
        return pictureDto;
    }
}
