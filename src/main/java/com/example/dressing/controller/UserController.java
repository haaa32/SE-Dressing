//회원가입, 로그인 등 유저 관리 컨트롤러
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
//import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor //(lombok) userService 필드를 매개변수로 갖는 UserController 생성자 자동생성
public class UserController {
    //스프링에서 사용하는 객체 사용 방법
    //생성자 주입(공식 느낌)
    public final UserService userService; //UserService 객체를 주입받는다 (컨트롤러가 서비스의 자원(메서드,필드) 등을 이용 가능하다)

    // 회원가입 페이지 출력 요청
    @GetMapping("/user/join")
    public String joinForm() {
        return "join";
    } //join에서 보낸 데이터를 받는 메소드가 없어 405 ERROR 가 뜬다 //login은 아직 html조차 존재하지 않아서 404 ERROR

    // 회원가입 페이지 폼 작성 데이터 받기
    @PostMapping("/user/join")
    /*public String join(@RequestParam("userName") String userName, //변수에 input 태그의 name 값 저장
                              @RequestParam("userId") String userId,
                              @RequestParam("userPassword") String userPassword) { */
    public String join(@ModelAttribute UserDTO userDTO) { //클래스 변수와 name이 일치하면 값이 저장된다
        System.out.println("UserController.join"); //soutm
        //System.out.println("userName = " + userName + ", userId = " + userId + ", userPassword = " + userPassword); //soutp
        System.out.println("userDTO = " + userDTO);

        userService.join(userDTO); //회원가입 로직

        return "login"; //회원가입 후 로그인 창
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession httpSession) { //아이디와 비밀번호 받아옴, 세션 정보까지
        UserDTO loginResult = userService.login(userDTO); //로그인 결과를 확인하기 위해

        if(loginResult != null) {
            //로그인 성공
            //로그인 유지를 하기 위해 세션 정보 추가** 이건 아직 제대로 몰겟음 (세션에 로그인 했던 회원의 아이디를 담아놓는다)
            httpSession.setAttribute("loginUserId", loginResult.getUserId()); // loginUserId 에 오른쪽 값이 들어가고 main.html로 전달된다?
            return "main";
        }
        else {
            //로그인 실패
            return "login";
        }
    }

    //회원목록 출력
    @GetMapping("/user/")
    public String findAll(Model model) {
        List<UserDTO> userDTOList = userService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("userList", userDTOList);
        return "list"; //list.html 로 모델을 가져갈 것임
    }

    //회원 상세정보 (아이디를 이용해 찾음)
    @GetMapping("/user/{id}") //경로상의 변수를 {} 안에 담음 => PathVariable: 이를 받는 어노테이션, 경로상의 값을 담아옴
    public String findByID(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.findByID(id);
        model.addAttribute("user", userDTO);
        return "detail";
    }

    //회원정보 수정 (html에서 정보 받았을 때)
    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        String myUserId = (String) session.getAttribute("loginUserId");
        UserDTO userDTO = userService.updateForm(myUserId);
        model.addAttribute("updateUser", userDTO);
        return "update";
    }

    //회원정보 수정 (정보 수정 폼을 작성받고, html로 다시 넘길 때)
    @PostMapping("/user/update")
    public String update(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        //리다이렉트: 컨트롤러 메소드의 끝나고 다시 다른 컨트롤러 메소드 접속(다른 애 주소)을 요청한다
        return "redirect:/user/" + userDTO.getId();
    }

    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        //바로 list.html로 가게 된다면 model이 없어 정보가 없이 전달된다. 이 때 리다이렉트 이용!!!
        return "redirect:/user/"; //redirect 뒤에는 무조건 주소가 온다 (list.html이 아님!!)
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate(); //세션을 무효화 한다
        return "index";
    }
}
