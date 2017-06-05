package com.company.core.repository;

import com.company.core.entity.Picture;
import com.company.core.entity.PictureDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDescriptorRepository extends JpaRepository<PictureDescriptor, Long> {
    PictureDescriptor findOneByPicture(Picture picture);
}
