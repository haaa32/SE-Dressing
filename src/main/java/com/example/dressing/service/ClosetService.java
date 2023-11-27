package com.example.dressing.service;

import com.example.dressing.Component.PythonModelComponent;
import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.entity.ClosetInfoEntity;
import com.example.dressing.entity.ImageData;
import com.example.dressing.entity.UserEntity;
import com.example.dressing.repository.ClosetInfoRepository;
import com.example.dressing.repository.ClosetRepository;

import com.example.dressing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ClosetService {
    private final PythonModelComponent pythonModelComponent;

    private final ClosetRepository closetRepository;
    private final UserRepository userRepository;
    private final ClosetInfoRepository closetInfoRepository;
    private final String fileStoragePath = "C:/Img_SW/"; // Define the base directory

    public Long saveFile(MultipartFile files, Long loginId) throws IOException {
        if (files.isEmpty()) {
            return null;
        }

        String origName = files.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = origName.substring(origName.lastIndexOf("."));
        String savedName = uuid + extension;

        // 임시로 컴퓨터에 이미지 저장
        String tempImagePath = fileStoragePath + savedName;
        File tempFile = new File(tempImagePath);
        files.transferTo(tempFile); // 일단 컴퓨터에 이미지 저장 (밑에서 삭제할 거임)

        // 저장한 이미지를 파이썬 모델을 통해 라벨 얻기
        String label = pythonModelComponent.getLabel(tempImagePath);
        System.out.println("==="+label+"===");

        String userDirectory = fileStoragePath + loginId + "/";
        String userLabelDirectory = userDirectory + label + "/";
        String savedPath = userLabelDirectory + savedName; // 경로: 유저id/라벨명/이미지.jpg 형태로 저장

        createDirectoryIfNotExists(userLabelDirectory); // 폴더 없으면 생성

        UserEntity userEntity = userRepository.findById(loginId).get();
        ClosetInfoEntity closetInfoEntity = closetInfoRepository.findByLabel(label).get();

        ClosetEntity file = ClosetEntity.builder()
                .user(userEntity)
                .closetInfoEntity(closetInfoEntity)
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        //files.transferTo(new File(savedPath)); // 실제 컴퓨터 경로에 저장
        File savedFile = new File(savedPath);
        Files.copy(tempFile.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING); // 컴퓨터 파일 복사 (경로 옮기기)
        deleteFileFromSystem(tempImagePath); // 임시 경로 이미지 삭제

        ClosetEntity savedEntity = closetRepository.save(file);

        return savedEntity.getId();
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

    // 이미지를 삭제하는 메소드
    public void deleteImage(Long imageId, Long userId) {
        // 지정된 이미지 ID로 ClosetEntity를 찾기
        ClosetEntity closetEntity = closetRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found"));
        // 찾은 ClosetEntity에서 파일 경로를 가져옴
        String filePath = closetEntity.getSavedPath();
        // 데이터베이스에서 ClosetEntity를 삭제
        closetRepository.delete(closetEntity);
        // 시스템에서 실제 파일을 삭제
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

    public List<ClosetEntity> findUserPhotosByCategory(Long loginId, String category) {
        return closetRepository.findUserPhotosByCategory(loginId, category);
    }

    public List<ImageData> toImageDataList(List<ClosetEntity> closetEntityList) throws IOException {
        // 이미지 데이터를 처리하기 위한 리스트를 생성
        List<ImageData> imageDataList = new ArrayList<>();
        for (ClosetEntity photo : closetEntityList) {
            if (photo == null) {
                imageDataList.add(null);
                continue;
            }
            ImageData imageData = new ImageData();
            imageData.setBase64Image(getBase64Image(photo.getSavedPath())); // 이미지를 Base64 형식으로 변환하여 저장
            imageData.setId(photo.getId()); // 이미지 ID를 설정
            imageDataList.add(imageData); // 리스트에 추가
        }
        return imageDataList;
    }


}
