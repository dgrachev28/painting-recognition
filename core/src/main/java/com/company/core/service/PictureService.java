package com.company.core.service;

import com.company.core.dto.PictureDto;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    PictureDto recognize(MultipartFile image);
}
