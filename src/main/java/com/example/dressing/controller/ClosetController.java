package com.example.dressing.controller;

import com.example.dressing.dto.ClosetDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClosetController {
    @GetMapping("/user/main")
    public String saveFile() {
        return "main";
    }

    @PostMapping("/user/main")
    public String save(@ModelAttribute ClosetDTO fileUploadDTO) {
        return null;
    }
}
