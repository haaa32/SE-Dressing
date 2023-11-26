package com.example.dressing.controller;

import com.example.dressing.Component.OtherComponent;
import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.entity.ImageData;
import com.example.dressing.service.ClosetService;
import com.example.dressing.service.CoordiService;
import com.example.dressing.service.UserService;
import com.example.dressing.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class CoordiController {
    private final WeatherService weatherService;
    private final UserService userService;
    private final OtherComponent otherComponent;
    public final ClosetService closetService;
    public final CoordiService coordiService;

    @GetMapping("/coordi")
    public String coordiForm(Model model, HttpSession session, HttpServletResponse response) throws ExecutionException, InterruptedException, IOException {
        String weatherData = weatherService.getDaeguWeather().get();
        model.addAttribute("weatherData", weatherData);

        Long userId = (Long) session.getAttribute("loginId");
        UserDTO userDTO = userService.findByID(userId);

        int numUserCoordi = userDTO.getNumUserCoordi();
        int numLimit = userService.getNumLimit(userDTO.getUserRank());

        // 추천받기 횟수 증가 및 DB에 저장
        if (numUserCoordi < numLimit) {
            userDTO.setNumUserCoordi(numUserCoordi + 1);
            userService.saveUser(userDTO);

            List<ClosetEntity> recommendedClosetEntityList = coordiService.recommendCoordiByUser
                    (userId, weatherService.getDaeguTempCelsius(weatherData));
            // 이미지 데이터를 처리하기 위한 리스트를 생성
            List<ImageData> imageDataList = new ArrayList<>();
            for (ClosetEntity photo : recommendedClosetEntityList) {
                ImageData imageData = new ImageData();
                imageData.setBase64Image(closetService.getBase64Image(photo.getSavedPath())); // 이미지를 Base64 형식으로 변환하여 저장
                imageData.setId(photo.getId()); // 이미지 ID를 설정
                imageDataList.add(imageData); // 리스트에 추가
            }

            // 모델에 이미지 데이터 리스트를 추가
            model.addAttribute("imagesData", imageDataList);

        } else {
            // 추천 가능 횟수 소진 메시지 전달 및 main 페이지로 리디렉션
            otherComponent.AlertMessage(response, "The chance is over!");
            //return "redirect:/main";
            return "coordi";
        }

        model.addAttribute("numUserCoordi", userDTO.getNumUserCoordi());
        model.addAttribute("numLimit", numLimit);

        return "coordi";
    }
}