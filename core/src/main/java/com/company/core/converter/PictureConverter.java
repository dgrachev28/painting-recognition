package com.company.core.converter;

import com.company.core.dto.PictureDto;
import com.company.core.entity.Picture;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class PictureConverter {
    public static PictureDto toDto(Picture picture) throws IOException {
        if (picture == null) {
            return null;
        }
        PictureDto pictureDto = new PictureDto();
        pictureDto.setDescription(picture.getDescription());
        pictureDto.setGallery(picture.getGallery());
        pictureDto.setShortInfo(picture.getShortInfo());
        pictureDto.setTitle(picture.getTitle());
        pictureDto.setYear(picture.getYear());
        pictureDto.setImageBase64(
                new String(Base64.getEncoder().encode(
                FileUtils.readFileToByteArray(new File(picture.getImagePath())))));
        return pictureDto;
    }
}
