package com.company.config;

import com.company.core.repository.PictureRepository;
import com.company.core.service.FileService;
import com.company.crawler.ArtCatalogParser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {PictureRepository.class, ArtCatalogParser.class, FileService.class})
public class MainConfig {
}
