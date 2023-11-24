package com.example.dressing.controller;

import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.entity.ImageData;
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
        try {
            // 날씨 서비스를 통해 대구의 날씨 정보를 가져옴
            String weatherData = weatherService.getDaeguWeather().get();
            model.addAttribute("weatherData", weatherData);

            // 세션에서 로그인 ID를 가져옴.
            Long loginId = (Long) session.getAttribute("loginId");
            // 사용자의 사진을 가져옴.
            List<ClosetEntity> userPhotos = closetService.getUserPhotos(loginId);

            // 이미지 데이터를 처리하기 위한 리스트를 생성
            List<ImageData> imageDataList = new ArrayList<>();
            for (ClosetEntity photo : userPhotos) {
                ImageData imageData = new ImageData();
                imageData.setBase64Image(closetService.getBase64Image(photo.getSavedPath())); // 이미지를 Base64 형식으로 변환하여 저장
                imageData.setId(photo.getId()); // 이미지 ID를 설정
                imageDataList.add(imageData); // 리스트에 추가
            }

            // 모델에 이미지 데이터 리스트를 추가
            model.addAttribute("imagesData", imageDataList);

        } catch (InterruptedException | ExecutionException e) {
            // 예외 처리: 날씨 정보나 사진을 가져오는 데 실패한 경우
            e.printStackTrace();
            model.addAttribute("error", "날씨 정보나 사진을 가져오는 데 실패했습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "main"; // 메인 페이지 템플릿 반환
    }
}
