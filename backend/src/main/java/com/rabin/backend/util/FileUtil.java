package com.rabin.backend.util;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private FileUtil(){}
    public static String saveFile(MultipartFile file, String folder) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/" + folder);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());
            return "/uploads/" + folder + "/" + fileName; // URL returned
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
