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

    public Resource serveImage(Long userId, String imageName) {
        // 사용자의 이미지 폴더 경로를 설정합니다.
        String userImageDirectory = "C:/Img_SW/" + userId + "/"; // 사용자의 이미지가 저장된 경로에 따라 수정해야 합니다.

        // 요청받은 이미지 경로를 찾습니다.
        Path imagePath = Paths.get(userImageDirectory).resolve(imageName);

        // 이미지를 로드하여 Resource 형태로 반환합니다.
        return new FileSystemResource(imagePath);
    }

    public String getBase64Image(String imagePath) throws IOException {
        File file = new File(imagePath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes);
    }

    public void deleteImage(Long imageId, Long userId) {
        ClosetEntity closetEntity = closetRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found"));
        String filePath = closetEntity.getSavedPath();
        closetRepository.delete(closetEntity);
        deleteFileFromSystem(filePath);
    }

    private void deleteFileFromSystem(String filePath) {
        try {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("이미지 파일이 성공적으로 삭제되었습니다");
            } else {
                System.out.println("이미지 파일 삭제에 실패했습니다");
            }
        } catch (Exception e) {
            System.out.println("이미지 파일 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

}
