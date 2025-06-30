package com.example.Polimart.controller;

import com.example.Polimart.model.Media;
import com.example.Polimart.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<Media> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        
        String userEmail = getCurrentUserEmail();
        Media uploadedMedia = mediaService.uploadFile(file, userEmail, description);
        return ResponseEntity.ok(uploadedMedia);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Media>> listUserMedia() {
        String userEmail = getCurrentUserEmail();
        List<Media> mediaList = mediaService.listUserMedia(userEmail);
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping("/list/{fileType}")
    public ResponseEntity<List<Media>> listUserMediaByType(@PathVariable String fileType) {
        String userEmail = getCurrentUserEmail();
        List<Media> mediaList = mediaService.listUserMediaByType(userEmail, fileType);
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping("/download/{mediaId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long mediaId) throws IOException {
        String userEmail = getCurrentUserEmail();
        Resource resource = mediaService.downloadFile(mediaId, userEmail);
        
        // Get the original filename for download
        Media media = mediaService.getMediaById(mediaId);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + media.getOriginalFileName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<String> deleteMedia(@PathVariable Long mediaId) throws IOException {
        String userEmail = getCurrentUserEmail();
        mediaService.deleteMedia(mediaId, userEmail);
        return ResponseEntity.ok("Media deleted successfully");
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
} 