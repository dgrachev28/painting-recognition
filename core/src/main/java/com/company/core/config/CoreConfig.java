package com.company.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan({"com.company.recognition", "com.company.core"})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CoreConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> null;
    }
}
