package com.steph.upload;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final FileStorageService fileStorageService;

    public UploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> uploadUserImage(@RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = fileStorageService.saveUserImage(file);

        return ResponseEntity.ok(Map.of(
                "imageUrl", imageUrl
        ));
    }

    @PostMapping("/post")
    public ResponseEntity<Map <String, String>> uploadPostImage(@RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = fileStorageService.savePostImage(file);

        return ResponseEntity.ok(Map.of(
                "imageUrl", imageUrl
        ));
    }
}
