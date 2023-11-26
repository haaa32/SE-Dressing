//대표 컨트롤러
package com.example.dressing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    @RequestMapping("/api/login")
    @ResponseBody
    public String login() {
        return "login";
    }
}
