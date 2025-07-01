package com.example.Polimart.service;

import com.example.Polimart.model.Media;
import com.example.Polimart.model.User;
import com.example.Polimart.repository.MediaRepository;
import com.example.Polimart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private final Path fileStorageLocation;

    public MediaService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Media uploadFile(MultipartFile file, String userEmail, String description) throws IOException {
        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        // Get user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate unique filename
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // Save file to disk
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Save metadata to database
        Media media = new Media();
        media.setFileName(fileName);
        media.setOriginalFileName(originalFileName);
        media.setFileType(file.getContentType());
        media.setFileSize(file.getSize());
        media.setFilePath(targetLocation.toString());
        media.setUser(user);
        media.setDescription(description);

        return mediaRepository.save(media);
    }

    public Resource downloadFile(Long mediaId, String userEmail) throws IOException {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        // Check if user owns the file or has permission
        if (!media.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Access denied");
        }

        Path filePath = Paths.get(media.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("File not found");
        }
    }

    public List<Media> listUserMedia(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mediaRepository.findByUserOrderByUploadedAtDesc(user);
    }

    public List<Media> listUserMediaByType(String userEmail, String fileType) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mediaRepository.findByUserAndFileTypeContainingOrderByUploadedAtDesc(user, fileType);
    }

    public void deleteMedia(Long mediaId, String userEmail) throws IOException {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        // Check if user owns the file
        if (!media.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Access denied");
        }

        // Delete file from disk
        Path filePath = Paths.get(media.getFilePath());
        Files.deleteIfExists(filePath);

        // Delete from database
        mediaRepository.delete(media);
    }

    public Media getMediaById(Long mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));
    }
} 