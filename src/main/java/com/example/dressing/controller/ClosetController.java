package com.example.dressing.controller;

import com.example.dressing.service.ClosetService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
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
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("files") List<MultipartFile> files) throws IOException {
        closetService.saveFile(file);

        for (MultipartFile multipartFile : files) {
            closetService.saveFile(multipartFile);
        }

        return "redirect:/"; // redirect:/
    }

}