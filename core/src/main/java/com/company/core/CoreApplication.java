package com.company.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.company.recognition")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
