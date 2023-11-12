package com.example.dressing.controller;

import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.service.ClosetService;
import com.example.dressing.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Controller
public class ShowMainController {
    private final WeatherService weatherService;
    private final ClosetService closetService;

    public ShowMainController(WeatherService weatherService, ClosetService closetService) {
        this.weatherService = weatherService;
        this.closetService = closetService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        // 기존 내용과 함께 해당 사용자의 사진 목록을 불러오는 기능을 사용
        try {
            String weatherData = weatherService.getDaeguWeather().get();
            model.addAttribute("weatherData", weatherData);

            Long loginId = (Long) session.getAttribute("loginId");
            List<ClosetEntity> userPhotos = closetService.getUserPhotos(loginId);

            List<String> base64Images = new ArrayList<>();
            for (ClosetEntity photo : userPhotos) {
                String base64Image = closetService.getBase64Image(photo.getSavedPath());
                base64Images.add(base64Image);
            }

            model.addAttribute("base64Images", base64Images);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            model.addAttribute("error", "날씨 정보나 사진을 가져오는 데 실패했습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "main"; // 메인 페이지 템플릿 반환
    }

}