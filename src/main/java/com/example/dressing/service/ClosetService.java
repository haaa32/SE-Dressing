package com.example.dressing.service;

import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.entity.UserEntity;
import com.example.dressing.repository.ClosetRepository;

import com.example.dressing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClosetService {

    private final ClosetRepository closetRepository;
    private final UserRepository userRepository;
    private final String fileStoragePath = "C:/Img_SW/"; // Define the base directory

    public Long saveFile(MultipartFile files, Long loginId) throws IOException {
        if (files.isEmpty()) {
            return null;
        }

        String origName = files.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = origName.substring(origName.lastIndexOf("."));
        String savedName = uuid + extension;

        // Adjust the file storage path to include the user's ID
        String userDirectory = fileStoragePath + loginId + "/";
        String savedPath = userDirectory + savedName;

        createDirectoryIfNotExists(userDirectory); // 폴더 없으면 생성

        UserEntity userEntity = userRepository.findById(loginId).get();

        ClosetEntity file = ClosetEntity.builder()
                .user(userEntity)
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        files.transferTo(new File(savedPath));

        ClosetEntity savedFile = closetRepository.save(file);

        return savedFile.getId();
    }

    private void createDirectoryIfNotExists(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public List<ClosetEntity> getUserPhotos(Long userId) {
        return closetRepository.findUserPhotos(userId);
    }

    public String getBase64Image(String imagePath) throws IOException {
        File file = new File(imagePath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes);
    }
}
