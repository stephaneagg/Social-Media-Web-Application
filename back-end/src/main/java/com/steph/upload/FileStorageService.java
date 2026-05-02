package com.steph.upload;

import com.steph.exceptions.FileValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path userUploadPath = Paths.get("uploads/users");
    private final Path postUploadPath = Paths.get("uploads/posts");


    public String saveUserImage(MultipartFile file) throws IOException {
        validateFile(file);
        return save(file, userUploadPath, "uploads/users/");
    }

    public String savePostImage(MultipartFile file) throws IOException {
        validateFile(file);
        return save(file, postUploadPath, "uploads/posts/");
    }

    public String save(MultipartFile file, Path uploadPath, String urlPrefix) throws IOException {
        Files.createDirectories(uploadPath);

        String originalFilename = file.getOriginalFilename();

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String filename = UUID.randomUUID() + extension;

        Path target = uploadPath.resolve(filename);

        file.transferTo(target);

        return urlPrefix + filename;
    }

    public void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new FileValidationException("File is empty");
        }

        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new FileValidationException("Only image files are allowed");
        }

        if (file.getSize() > 5_000_000) {
            throw new FileValidationException("File too large");
        }

    }
}
