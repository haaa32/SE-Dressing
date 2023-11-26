package com.example.dressing.service;

import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.repository.ClosetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CoordiService {

    private final ClosetRepository closetRepository;

    // 사용자 기반 옷추천
    public List<ClosetEntity> recommendCoordiByUser(Long loginId, Double temp) {
        List<String> categoryList = new ArrayList<>(Arrays.asList("outer", "top", "bottom", "shoes", "bag"));
        List<ClosetEntity> resultClosetEntityList= new ArrayList<>();

        // 나중에 중복체크
        for(String category : categoryList) {
            List<ClosetEntity> closetEntityList = closetRepository.findUserPhotosByCategory(loginId, category); // 유저, 카테고리 옷 리스트
            if(closetEntityList.size() == 0) {
                //resultClosetEntityList.add(null);
                continue;
            }
            Random random = new Random();
            ClosetEntity randomClosetEntity = closetEntityList.get(random.nextInt(closetEntityList.size())); // 랜덤으로 옷 선택

            resultClosetEntityList.add(randomClosetEntity);
            System.out.println(randomClosetEntity);
        }

        System.out.println("랜덤 옷선택 결과" + resultClosetEntityList);

        return resultClosetEntityList;
    }

    // 사용자가 각 필수 카테고리에 최소 하나의 아이템을 가지고 있는지 확인하는 메서드
    public boolean hasRequiredItems(Long userId) {
        // 필수 카테고리 목록
        List<String> requiredCategories = Arrays.asList("top", "bag");
        for (String category : requiredCategories) {
            // 각 카테고리에 대한 사용자의 아이템 목록을 가져옴
            List<ClosetEntity> items = closetRepository.findUserPhotosByCategory(userId, category);
            // 해당 카테고리의 아이템이 없다면 false 반환
            if (items == null || items.isEmpty()) {
                return false;
            }
        }
        // 모든 필수 카테고리에 대해 최소 하나의 아이템이 있다면 true 반환
        return true;
    }
}