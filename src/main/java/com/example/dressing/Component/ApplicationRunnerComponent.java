package com.example.dressing.Component;

import com.example.dressing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//Spring boot 구동 시 DressingApplication이 실행되고 나서 실행되는 애
public class ApplicationRunnerComponent implements ApplicationRunner {
    public final UserService userService;

    @Override
    // 사용자 rank 업데이트 (서버를 상시가동 할 수 없기 때문에 서버를 실행할 때마다 업데이트 해주기 위함)
    public void run(ApplicationArguments args) throws Exception {
        //서버 실행하자마자 사용자 rank 업데이트 해주기
        userService.updateRank();
    }
}
