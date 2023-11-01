package com.example.dressing.dto;

import com.example.dressing.entity.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString // 필드 값 확인할 때 사용함
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class ClosetDTO {
    private Long id;
    private UserEntity user;
    private String label;
    private String url;

    private MultipartFile closetFile; // main.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
}
