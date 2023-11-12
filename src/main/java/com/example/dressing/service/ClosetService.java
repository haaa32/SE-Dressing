package com.example.dressing.service;

import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.repository.ClosetRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClosetService {

    private final ClosetRepository closetRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Long saveFile(MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            return null;
        }

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 UUID 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출 (예: .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = "static/images/" + savedName;

        // 실제 파일이 저장될 경로 설정
        Path path = Paths.get(uploadDir + File.separator + savedName);
        Files.createDirectories(path.getParent());
        Files.copy(files.getInputStream(), path);

        ClosetEntity entity = ClosetEntity.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath("static/images/" + savedName)
                .build();

        ClosetEntity savedFile = closetRepository.save(entity);
        return savedFile.getId();
    }
}
