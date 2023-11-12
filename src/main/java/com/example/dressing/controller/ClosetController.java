package com.example.dressing.controller;

import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.service.ClosetService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ClosetController {

    private final ClosetService closetService;

    @GetMapping("/upload")
    public String testUploadForm() {
        return "uploadTest";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("files") List<MultipartFile> files) throws IOException {
        closetService.saveFile(file);

        for (MultipartFile multipartFile : files) {
            closetService.saveFile(multipartFile);
        }

        return "redirect:/main"; // redirect:/
    }

//    @GetMapping("/main")
//    public String showImages(Model model) {
//        List<ClosetEntity> images = closetService.getAllImages(); // 이미지를 가져오는 메서드
//
//        model.addAttribute("images", images);
//        return "main"; // 이미지를 출력할 HTML 템플릿
//    }

}