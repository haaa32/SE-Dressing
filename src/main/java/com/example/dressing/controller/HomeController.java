//대표 컨트롤러
package com.example.dressing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
//@RequestMapping("/api")
public class HomeController {
    //기본 페이지 요청 메서드
    @RequestMapping("/api/login")
    @ResponseBody
    public String login() {
        return "login";
    }

}
