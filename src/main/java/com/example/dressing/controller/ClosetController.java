package com.example.dressing.controller;

import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.repository.ClosetRepository;
import com.example.dressing.service.ClosetService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ClosetController {

    private final ClosetService closetService;
    private ClosetRepository closetRepository;

    @GetMapping("/upload")
    public String testUploadForm() {
        return "uploadTest";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("files") List<MultipartFile> files, HttpSession httpSession) throws IOException {
        Long loginId = (long) httpSession.getAttribute("loginId");
        closetService.saveFile(file, loginId);

        for (MultipartFile multipartFile : files) {
            closetService.saveFile(multipartFile, loginId);
        }

        return "redirect:/main"; // redirect:/
    }

}