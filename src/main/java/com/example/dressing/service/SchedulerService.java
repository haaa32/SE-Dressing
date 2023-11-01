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
    private final UserRepository userRepository;

    //**** 이거 매일 12시에 할지 (+서버 시작할 때도 update 해줘야함) or 뭐 몇 분이나 몇 시간마다 업데이트 할지
    //매일 오전 12시에 모든 사용자들의 rank가 update됨
    @Scheduled(cron = "0 0 12 * * *") //(초, 분, 시간, 일, 월, 요일)
    public void updateRank() {
        LocalDateTime nowDateLime = LocalDateTime.now();

        List<UserEntity> userEntityList = userRepository.findAll();

        for (UserEntity userEntity : userEntityList) {
            // 사용자가 가입 후 지난 날짜 저장
            long between = ChronoUnit.DAYS.between(userEntity.getCreatedDate().toLocalDate(), nowDateLime.toLocalDate()); //현재 날짜 - 사용자 가입 날짜
            String tmpRank = "Bronze"; //update 할 rank 저장

            if(between >= 14 && between < 30) // 가입일 14일 이후면
                tmpRank = "Silver";
            else if (between >= 30 && between < 60) //가입일 30일 이후
                tmpRank = "Gold";
            else if (between >= 60) //가입일 60일 이후
                tmpRank = "Diamond";

            if(!tmpRank.equals(userEntity.getUserRank())) //rank가 변경되었다면 update
                userRepository.updateUserRank(tmpRank, userEntity.getId());
        }

    }

}
