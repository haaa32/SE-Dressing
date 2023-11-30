package com.example.dressing.controller;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.dto.ImageData;
import com.example.dressing.service.ClosetService;
import com.example.dressing.service.CoordiService;
import com.example.dressing.service.UserService;
import com.example.dressing.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class ShowMainController {
    private final WeatherService weatherService;
    private final ClosetService closetService;
    private final CoordiService coordiService;
    private final UserService userService;

    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        try {
            // 날씨 서비스를 통해 대구의 날씨 정보를 가져옴
            String weatherData = weatherService.getDaeguWeather().get();
            model.addAttribute("weatherData", weatherData);

            // 세션에서 로그인 ID를 가져옴.
            Long loginId = (Long) session.getAttribute("loginId");
            // 로그인 ID를 이용해 UserDTO 구하기
            UserDTO userDTO = userService.findByID(loginId); // main에 Rank 보내려고
            model.addAttribute("userRank", userDTO.getUserRank());
            // 세션에서 카테고리를 가져옴.
            String category = (String) session.getAttribute("category");
            // 사용자의 사진을 가져옴.
            List<ClosetEntity> userPhotos = new ArrayList<>();
            // 좋아요 싫어요에 쓰이는 코디 리스트 (2차원)
            List<List<ClosetEntity>> userHeartPhotos = new ArrayList<>();

            // 카테고리에 따라 사진 리스트 선택
            if(category.equals("like") || category.equals("dislike")) { // 좋아요, 싫어요
                userHeartPhotos = coordiService.getUserCoordis(loginId, category);

                List<List<ImageData>> heartImageDataList = new ArrayList<>();
                for (List<ClosetEntity> userHeart : userHeartPhotos)
                    heartImageDataList.add(closetService.toImageDataList(userHeart));
                //모델에 등록
                model.addAttribute("heartImagesDataLists", heartImageDataList);
            }
            else {
                if(category.equals("total")) // 전체
                    userPhotos = closetService.getUserPhotos(loginId);
                else // 나머지 옷 카테고리
                    userPhotos = closetService.findUserPhotosByCategory(loginId, category);

                // 이미지 데이터를 처리하기 위한 리스트를 생성
                List<ImageData> imageDataList = closetService.toImageDataList(userPhotos);
                // 모델에 이미지 데이터 리스트를 추가
                model.addAttribute("imagesDataList", imageDataList);
            }

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