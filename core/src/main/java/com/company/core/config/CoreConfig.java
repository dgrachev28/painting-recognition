package com.company.core.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.company.recognition", "com.company.core"})
public class CoreConfig {
}
