package com.company.config;

import com.company.component.FileService;
import com.company.crawler.Crawler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Crawler.class, FileService.class})
public class MainConfig {
}
