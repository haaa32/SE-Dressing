package com.example.dressing.controller;

import com.example.dressing.Component.OtherComponent;
import com.example.dressing.dto.UserDTO;
import com.example.dressing.service.UserService;
import com.example.dressing.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class CoordiController {
    private final WeatherService weatherService;
    private final UserService userService;
    private final OtherComponent otherComponent;

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
