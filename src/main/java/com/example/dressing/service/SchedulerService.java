package com.example.dressing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final UserService userService; //userService 의존성 받아와서 업데이트 하기

    //매일 오전 12시에 모든 사용자들의 rank가 update됨
    @Scheduled(cron = "0 0 0 * * *") //(초, 분, 시간, 일, 월, 요일)
    public void updateRankMidnight() {

        userService.updateRank();
    }

}
