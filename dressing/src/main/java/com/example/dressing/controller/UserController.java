//회원가입, 로그인 등 유저 관리 컨트롤러
package com.example.dressing.controller;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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
}
