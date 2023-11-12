package com.example.dressing.controller;
import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.service.ClosetService;
import com.example.dressing.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;
@Controller
public class WeatherController {
    private final WeatherService weatherService;
    private final ClosetService closetService;

    public WeatherController(WeatherService weatherService, ClosetService closetService) {
        this.weatherService = weatherService;
        this.closetService = closetService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model) {

        try {
            String weatherData = weatherService.getDaeguWeather().get();

            System.out.println("result : "+weatherData);
            model.addAttribute("weatherData", weatherData);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            model.addAttribute("error", "날씨 정보를 가져오는 데 실패했습니다.");
        }
        System.out.println("오류1");
        List<ClosetEntity> images = closetService.getAllImages(); // 이미지를 가져오는 메서드
        System.out.println("오류2");
        model.addAttribute("images", images);
        System.out.println("오류3");
        return "main"; // 메인 페이지 템플릿 반환
    }
}