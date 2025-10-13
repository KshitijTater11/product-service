package com.productservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path uploadDir = Paths.get("uploads");

    public String saveFile(MultipartFile file) throws IOException {
        Files.createDirectories(uploadDir);
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);
        file.transferTo(filePath);
        return "/uploads/" + fileName;
    }
}