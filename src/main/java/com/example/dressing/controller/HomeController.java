//대표 컨트롤러
package com.example.dressing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //기본 페이지 요청 메서드
    @GetMapping("/") // /주소가 요청되면 index 함수를 실행한다 (보통 홈은 기본으로 / 으로 표현된다)
    public String index() {
        return "login";
    }

}
