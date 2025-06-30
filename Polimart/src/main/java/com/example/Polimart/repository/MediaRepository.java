package com.example.Polimart.repository;

import com.example.Polimart.model.Media;
import com.example.Polimart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByUserOrderByUploadedAtDesc(User user);
    List<Media> findByUserAndFileTypeContainingOrderByUploadedAtDesc(User user, String fileType);
} 