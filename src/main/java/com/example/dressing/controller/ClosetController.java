package com.example.dressing.controller;

import com.example.dressing.dto.ClosetDTO;
import com.example.dressing.service.ClosetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ClosetController {
    private final ClosetService closetService;
    @GetMapping("/user/main")
    public String saveFile() {
        return "main";
    }

    @PostMapping("/user/main")
    public String save(@ModelAttribute ClosetDTO closetDTO)
    {
        closetService.save(closetDTO);
        return "main";
    }
}
