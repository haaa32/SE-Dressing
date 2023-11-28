package com.example.dressing.service;

import com.example.dressing.repository.ClosetInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import com.example.dressing.entity.ClosetInfoEntity;

import javax.annotation.PostConstruct;

@Service
public class ClosetInfoService {

    @Autowired
    private ClosetInfoRepository closetInfoRepository;

    // 클래스 인스턴스화 후 초기화를 위한 메소드
    @PostConstruct
    public void init() {
        insertClosetInfoData();
    }

    // 옷장 정보 데이터를 저장소에 삽입하는 메소드
    public void insertClosetInfoData() {
        System.out.println("insertClosetInfoData 메소드 호출됨");
        List<ClosetInfoEntity> entities = Arrays.asList(
                new ClosetInfoEntity("T-shirt", "Top", -5, 40),
                new ClosetInfoEntity("Trouser", "Bottom",0, 40),
                new ClosetInfoEntity("Pullover", "Top",-5, 22),
                new ClosetInfoEntity("Dress", "Top",12, 40),
                new ClosetInfoEntity("Coat", "Outer",-5, 16),
                new ClosetInfoEntity("Sandal", "Shoes",23, 40),
                new ClosetInfoEntity("Shirt", "Top",17, 27),
                new ClosetInfoEntity("Sneaker", "Shoes",0, 40),
                new ClosetInfoEntity("Bag", "Bag",0, 40),
                new ClosetInfoEntity("Ankle boot", "Shoes",-5, 11)
        );

        // 저장소에 모든 항목들을 저장
        closetInfoRepository.saveAll(entities);
    }
}