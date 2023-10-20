package com.spring.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebImgConfig implements WebMvcConfigurer {

    @Value("${img.upload.directory}")
    private String imgUploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("src/main/resources/static/product/**")
                .addResourceLocations(imgUploadDirectory);
    }
}