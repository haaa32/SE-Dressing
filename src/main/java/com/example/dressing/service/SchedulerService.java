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

    //**** �̰� ���� 12�ÿ� ���� (+���� ������ ���� update �������) or �� �� ���̳� �� �ð����� ������Ʈ ����
    //���� ���� 12�ÿ� ��� ����ڵ��� rank�� update��
    @Scheduled(cron = "0 0 12 * * *") //(��, ��, �ð�, ��, ��, ����)
    public void updateRank() {
        LocalDateTime nowDateLime = LocalDateTime.now();

        List<UserEntity> userEntityList = userRepository.findAll();

        for (UserEntity userEntity : userEntityList) {
            // ����ڰ� ���� �� ���� ��¥ ����
            long between = ChronoUnit.DAYS.between(userEntity.getCreatedDate().toLocalDate(), nowDateLime.toLocalDate()); //���� ��¥ - ����� ���� ��¥
            String tmpRank = "Bronze"; //update �� rank ����

            if(between >= 14 && between < 30) // ������ 14�� ���ĸ�
                tmpRank = "Silver";
            else if (between >= 30 && between < 60) //������ 30�� ����
                tmpRank = "Gold";
            else if (between >= 60) //������ 60�� ����
                tmpRank = "Diamond";

            if(!tmpRank.equals(userEntity.getUserRank())) //rank�� ����Ǿ��ٸ� update
                userRepository.updateUserRank(tmpRank, userEntity.getId());
        }

    }

}
