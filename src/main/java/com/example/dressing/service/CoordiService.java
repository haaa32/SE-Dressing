package com.example.dressing.service;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.entity.CoordiEntity;
import com.example.dressing.repository.ClosetRepository;
import com.example.dressing.repository.CoordiRepository;
import com.example.dressing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class CoordiService {

    private final ClosetRepository closetRepository;
    private final CoordiRepository coordiRepository;
    private final UserRepository userRepository;

    // 사용자 기반 옷추천
    public List<ClosetEntity> recommendCoordiByUser(Long loginId, Double tempCelsius) {
        List<String> categoryList = new ArrayList<>(Arrays.asList("outer", "top", "bottom", "shoes", "bag"));
        List<ClosetEntity> resultClosetEntityList= new ArrayList<>();

        // 나중에 중복체크
        for(String category : categoryList) {
            List<ClosetEntity> closetEntityList = closetRepository.findUserPhotosByCategory(loginId, category); // 유저, 카테고리 옷 리스트

            // 옷의 라벨마다 온도에 따라 추천되도록 (온도가 맞지 않으면 리스트에서 제거)
            Iterator it = closetEntityList.iterator();
            while (it.hasNext()) {
                ClosetEntity nextClosetEntity = (ClosetEntity) it.next();
                if(tempCelsius < nextClosetEntity.getClosetInfoEntity().getMinTemp() || tempCelsius > nextClosetEntity.getClosetInfoEntity().getMaxTemp())
                    it.remove();
            }

            // 추천할 옷이 없을때 (리스트에 남은 옷이 없으면)
            if(closetEntityList.size() == 0) {
                resultClosetEntityList.add(null);
                continue;
            }
            // 추천할 옷이 있을때
            Random random = new Random();
            ClosetEntity randomClosetEntity = closetEntityList.get(random.nextInt(closetEntityList.size())); // 랜덤으로 옷 선택

            resultClosetEntityList.add(randomClosetEntity);
            System.out.println(randomClosetEntity);
        }

        System.out.println("랜덤 옷선택 결과" + resultClosetEntityList);

        return resultClosetEntityList;
    }

    // List를 가지고 Coordi 테이블 저장
    public CoordiEntity saveByClosetList(UserDTO userDTO, List<ClosetEntity> closetEntityList) {
        CoordiEntity coordiEntity = CoordiEntity.builder()
                .userEntity(userRepository.findById(userDTO.getId()).get())
                .outerId((closetEntityList.get(0) != null)? closetEntityList.get(0).getId() : null)
                .topId((closetEntityList.get(1) != null)? closetEntityList.get(1).getId() : null)
                .bottomId((closetEntityList.get(2) != null)? closetEntityList.get(2).getId() : null)
                .shoesId((closetEntityList.get(3) != null)? closetEntityList.get(3).getId() : null)
                .bagId((closetEntityList.get(4) != null)? closetEntityList.get(4).getId() : null)
                .build();
        return coordiRepository.save(coordiEntity);
    }

    // 좋아요
    public void like(Long coordiId) {
        CoordiEntity coordiEntity = coordiRepository.findById(coordiId).get();
        coordiEntity.setHeart(1);
        coordiRepository.save(coordiEntity);
    }

    // 싫어요
    public void dislike(Long coordiId) {
        CoordiEntity coordiEntity = coordiRepository.findById(coordiId).get();
        coordiEntity.setHeart(-1);
        coordiRepository.save(coordiEntity);
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