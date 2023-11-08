package com.example.dressing.service;

import com.example.dressing.entity.UserEntity;
import com.example.dressing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
//    private final UserRepository userRepository;
    private final UserService userService; //userService 의존성 받아와서 업데이트 하기,, 근데 이거 문제 좀 있는듯

    //**** 이거 매일 12시에 할지 (+서버 시작할 때도 update 해줘야함) or 뭐 몇 분이나 몇 시간마다 업데이트 할지
    //매일 오전 12시에 모든 사용자들의 rank가 update됨
    @Scheduled(cron = "0 0 0 * * *") //(초, 분, 시간, 일, 월, 요일)
    public void updateRankMidnight() {

        userService.updateRank();
    }

}
