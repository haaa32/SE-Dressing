package com.example.dressing.Component;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
//프로그램에서 쓰이는 여러 뭐 기능들이 저장되어 있는 컴포넌트 클래스
public class OtherComponent {
    // Alert 팝업창 띄우기 함수 (멘트는 message)
    public void AlertMessage(HttpServletResponse response, String message) throws IOException {
        String script = String.format("<script> alert('%s');", message);

        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        out.println(script);
        out.println("history.go(-1); </script>"); // 이전 페이지로 돌아가는 명령 -> redirect랑 겹쳐서 에러날 때 있음
        out.close();
    }
}
