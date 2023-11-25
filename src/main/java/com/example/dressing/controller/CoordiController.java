package com.example.dressing.controller;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.service.UserService;
import com.example.dressing.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class CoordiController {
    public final WeatherService weatherService;
    public final UserService userService;

    @GetMapping("/coordi")
    public String coordiForm(Model model, HttpSession session) throws ExecutionException, InterruptedException {
        String weatherData = weatherService.getDaeguWeather().get();
        model.addAttribute("weatherData", weatherData);

        Long userId = (Long) session.getAttribute("loginId");
        UserDTO userDTO = userService.findByID(userId);
        int numLimit = userService.getNumLimit(userDTO.getUserRank());
        model.addAttribute("numLimit", numLimit);



        return "coordi";
    }
}
