package com.company.crawler.config;

import com.company.core.config.CoreConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.company.crawler"})
@Import(CoreConfig.class)
public class CrawlerConfig {
}
