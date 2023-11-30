package com.example.dressing.controller;

import com.example.dressing.service.ClosetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor // Lombok 라이브러리를 사용하여 생성자 주입을 자동화
@Controller
public class ClosetController {

    private final ClosetService closetService; // 의존성 주입을 통한 서비스 레이어의 연결

    @GetMapping("/upload")
    public String testUploadForm() {
        return "uploadTest";
    } // 파일 업로드 폼을 불러오는 메소드

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("files") List<MultipartFile> files, HttpSession httpSession) throws IOException {
        Long loginId = (long) httpSession.getAttribute("loginId"); // 세션에서 로그인 ID를 가져옴.

        for (MultipartFile multipartFile : files) {
            closetService.saveFile(multipartFile, loginId); // 업로드된 파일을 저장
        }
        return "redirect:/main"; // 파일 업로드 후 메인 페이지로 리다이렉트
    }

    @GetMapping("/deleteImage")
    public String deleteImage(@RequestParam("id") Long imageId, HttpSession httpSession) {
        Long loginId = (Long) httpSession.getAttribute("loginId"); // 세션에서 로그인 ID를 가져옴
        closetService.deleteImage(imageId, loginId); // 지정된 이미지를 삭제
        return "redirect:/main";  // 이미지 삭제 후 메인 페이지로 리다이렉트
    }

}