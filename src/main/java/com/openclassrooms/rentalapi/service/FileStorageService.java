package com.openclassrooms.rentalapi.service;

import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;
import com.openclassrooms.rentalapi.exception.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileStorageService {

    @Value("${rentalapi.upload.dir}")
    private String uploadDir;

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(INVALID_PICTURE);
        }

        String filename = String.format("%s_%s", UUID.randomUUID(), file.getOriginalFilename());

        Path destination = Paths.get(uploadDir).resolve(filename);
        log.info("Storing file to: {}", destination.toString());

        try {
            Files.createDirectories(destination.getParent());
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException(FILE_STORAGE_FAILED, e);
        }

        return String.format("/%s/%s", uploadDir, filename);
    }
}
