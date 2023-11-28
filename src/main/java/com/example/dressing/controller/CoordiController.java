package com.example.dressing.controller;

import com.example.dressing.Component.OtherComponent;
import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.entity.CoordiEntity;
import com.example.dressing.dto.ImageData;
import com.example.dressing.service.ClosetService;
import com.example.dressing.service.CoordiService;
import com.example.dressing.service.UserService;
import com.example.dressing.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

        // 사용자가 Top, Bottom, Shoes 카테고리에서 최소 한 개의 아이템을 갖고 있는지 확인
        boolean hasRequiredItems = coordiService.hasRequiredItems(userId);
        if (!hasRequiredItems) {
            otherComponent.AlertMessage(response, "Add more clothes!");
            return "coordi";
        }

        if(numUserCoordi >= numLimit) { // === 추천 횟수 소진 ===
            // 추천 가능 횟수 소진 메시지 전달 및 main 페이지로
            otherComponent.AlertMessage(response, "The chance is over!");
            //return "redirect:/main";
            return "coordi";
        }

        // === 추천 가능 ===
        userDTO.setNumUserCoordi(numUserCoordi + 1); // 추천 카운트 1 증가
        userService.saveUser(userDTO);

        // 옷 추천
        List<ClosetEntity> recommendedClosetEntityList = coordiService.recommendCoordiByUser
                (userId, weatherService.getDaeguTempCelsius(weatherData)); // 옷 추천 받기
        CoordiEntity savedCoordiEntity = coordiService.saveByClosetList(userDTO, recommendedClosetEntityList); //추천 코디 DB 저장
        session.setAttribute("coordiId", savedCoordiEntity.getId()); // 추천받은 코디의 아이디

        // 이미지 데이터를 처리하기 위한 리스트를 생성
        List<ImageData> imageDataList = closetService.toImageDataList(recommendedClosetEntityList);

        // 모델에 이미지 데이터 리스트를 추가
        model.addAttribute("imagesDataList", imageDataList);

        model.addAttribute("numUserCoordi", userDTO.getNumUserCoordi());
        model.addAttribute("numLimit", numLimit);

        return "coordi";
    }

    @GetMapping("/coordi/like")
    @ResponseBody
    public String likeCoordi(HttpSession session) {
        Long coordiId = (Long) session.getAttribute("coordiId");
        coordiService.like(coordiId);

//        return "redirect:/coordi";
        return handleUserReaction(session, true);
    }

    @GetMapping("/coordi/dislike")
    @ResponseBody
    public String dislikeCoordi(HttpSession session) {
        Long coordiId = (Long) session.getAttribute("coordiId");
        coordiService.dislike(coordiId);

//        return "redirect:/coordi";
        return handleUserReaction(session, false);
    }

    // 사용자 반응 처리 공통 메소드
    private String handleUserReaction(HttpSession session, boolean isLike) {
        Long userId = (Long) session.getAttribute("loginId");
        UserDTO userDTO = userService.findByID(userId);

        // JSON 형태로 numUserCoordi 값을 반환
        return "{\"numUserCoordi\": " + userDTO.getNumUserCoordi() + "}";
    }
}
