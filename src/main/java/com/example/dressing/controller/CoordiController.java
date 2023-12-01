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
// 코디 컨트롤러
public class CoordiController {
    private final WeatherService weatherService;
    private final UserService userService;
    private final OtherComponent otherComponent;
    public final ClosetService closetService;
    public final CoordiService coordiService;

    // 옷 추천 페이지 출력 요청
    @GetMapping("/coordi")
    public String coordiForm(Model model, HttpSession session, HttpServletResponse response) throws ExecutionException, InterruptedException, IOException {
        // 현재 날씨를 받아온다
        String weatherData = weatherService.getDaeguWeather().get();
        model.addAttribute("weatherData", weatherData);

        // 세션에서 로그인한 아이디를 받아온다
        Long userId = (Long) session.getAttribute("loginId");
        UserDTO userDTO = userService.findByID(userId); // 로그인 아이디를 이용해 DTO를 구한다

        // 사용자의 코디 횟수 / 최대 코디 횟수
        int numUserCoordi = userDTO.getNumUserCoordi();
        int numLimit = userService.getNumLimit(userDTO.getUserRank());

        // 사용자가 Top, Bottom, Shoes 카테고리에서 최소 한 개의 아이템을 갖고 있는지 확인
        boolean hasRequiredItems = coordiService.hasRequiredItems(userId);
        if (!hasRequiredItems) {
            otherComponent.AlertMessage(response, "Add more clothes!");
            return "coordi";
        }

        // === 추천 횟수 소진 ===
        if(numUserCoordi >= numLimit) {
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
                (userId, weatherService.getDaeguTempCelsius(weatherData)); // 날씨에 맞는 옷 추천 받기
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

    // 추천받은 코디 좋아요 누를시
    @GetMapping("/coordi/like")
    @ResponseBody // HTTP 응답 본문에 직접 내용을 반환
    public String likeCoordi(HttpSession session) {
        Long coordiId = (Long) session.getAttribute("coordiId"); // 세션에서 코디 ID를 가져옴.
        coordiService.like(coordiId); // 코디에 '좋아요'를 처리

//        return "redirect:/coordi";
        return handleUserReaction(session, true); // 사용자 반응을 처리하고 결과를 반환
    }

    //추천받은 코디 싫어요 누를시
    @GetMapping("/coordi/dislike")
    @ResponseBody // HTTP 응답 본문에 직접 내용을 반환
    public String dislikeCoordi(HttpSession session) {
        Long coordiId = (Long) session.getAttribute("coordiId");  // 세션에서 코디 ID를 가져옴
        coordiService.dislike(coordiId);  // 코디에 '싫어요'를 처리

//        return "redirect:/coordi";
        return handleUserReaction(session, false); // 사용자 반응을 처리하고 결과를 반환
    }

    // 사용자 반응 처리 공통 메소드
    private String handleUserReaction(HttpSession session, boolean isLike) {
        Long userId = (Long) session.getAttribute("loginId"); // 세션에서 사용자 ID를 가져옴.
        UserDTO userDTO = userService.findByID(userId); // 사용자 정보를 가져옴

        // JSON 형태로 numUserCoordi 값을 반환
        return "{\"numUserCoordi\": " + userDTO.getNumUserCoordi() + "}";
    }
}