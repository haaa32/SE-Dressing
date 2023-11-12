package com.example.dressing.controller;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    // ** 나중에 매핑 주소 바꾸기 ex /user -> /admin/users

    //회원목록 출력
    @GetMapping("/user")
    public String findAll(Model model) {
        List<UserDTO> userDTOList = userService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("userList", userDTOList);
        return "/admin/list"; //list.html 로 모델을 가져갈 것임
    }

    //회원 상세정보 (아이디를 이용해 찾음)
    @GetMapping("/user/{id}") //경로상의 변수를 {} 안에 담음 => PathVariable: 이를 받는 어노테이션, 경로상의 값을 담아옴
    public String findByID(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.findByID(id);
        model.addAttribute("user", userDTO);

        userDTO.getCreatedDate().toLocalDate();
        return "/admin/detail";
    }

    // 회원정보 수정을 관리자 페이지에 넣음으로써 좀 달라짐 (list.html 출력시 user 하나마다 출력되게)
    //회원정보 수정 (html에서 정보 받았을 때)
    @GetMapping("/user/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.updateForm(id);
        model.addAttribute("updateUser", userDTO);
        return "/admin/update";
    }

    //회원정보 수정 (정보 수정 폼을 작성받고, html로 다시 넘길 때)
    @PostMapping("/user/update")
    public String update(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        //리다이렉트: 컨트롤러 메소드의 끝나고 다시 다른 컨트롤러 메소드 접속(다른 애 주소)을 요청한다,,(Post 매핑의 중복 방지)
        return "redirect:/user/"; //ㅇㅎ 리다이렉트 -> html 파일 말고 getmapping 하는 거인듯?
    }

    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        //바로 list.html로 가게 된다면 model이 없어 정보가 없이 전달된다. 이 때 리다이렉트 이용!!!
        return "redirect:/user/"; //redirect 뒤에는 무조건 주소가 온다 (list.html이 아님!!)
    }
}
