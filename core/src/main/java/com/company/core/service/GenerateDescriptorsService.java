package com.company.core.service;

import com.company.core.entity.Picture;

public interface GenerateDescriptorsService {
    void generateOne(Picture picture);
    void generateAll();
}
