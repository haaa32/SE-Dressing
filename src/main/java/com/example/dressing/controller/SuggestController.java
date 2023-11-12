package com.example.dressing.controller;

import com.example.dressing.dto.SuggestDTO;
import com.example.dressing.entity.SuggestEntity;
import com.example.dressing.service.SuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SuggestController {
    private final SuggestService suggestService;

    //사용자 건의하기 페이지
    @GetMapping("/suggest/save")
    public String saveForm() {
        return "suggest";
    }

    //사용자 건의하기 페이지에서 등록 버튼 누르면
    @PostMapping("/suggest/save")
    public String save(@ModelAttribute SuggestDTO suggestDTO, HttpSession httpSession) {
        suggestDTO.setUid((long) httpSession.getAttribute("loginId"));
        suggestDTO.setUserId((String) httpSession.getAttribute("loginUserId")); //굳이 안 해도 되긴함
        suggestService.save(suggestDTO);
        return "main";
    }

    //관리자 건의함 페이지 출력
    @GetMapping("/suggest/board")
    public String boardFindAll(Model model) {
        List<SuggestDTO> suggestDTOList = suggestService.findAll();
        model.addAttribute("suggestList", suggestDTOList);
        return "/admin/board";
    }
}