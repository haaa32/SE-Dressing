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
//    @GetMapping("/") // /주소가 요청되면 index 함수를 실행한다 (보통 홈은 기본으로 / 으로 표현된다)
//    public String index() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login() {
//        return ResponseEntity.ok("success");
//    }

    @RequestMapping("/api/login")
    @ResponseBody
    public String login() {
        return "login";
    }


}
