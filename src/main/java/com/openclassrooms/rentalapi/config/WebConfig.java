package com.openclassrooms.rentalapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class for serving uploaded files as static resources.
 * Maps the upload directory to a public URL path.
 * The upload directory is specified in the application properties and the .env
 * file.
 * Here, UPLOAD_DIR is set to "uploads/prictures".
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${rentalapi.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        log.info("Serving files from: {}", uploadPath.toString());

        String urlPathString = String.format("/%s/**", uploadDir);
        registry.addResourceHandler(urlPathString)
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
