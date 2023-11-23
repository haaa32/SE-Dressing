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

    @PostConstruct
    public void init() {
        insertClosetInfoData();
    }

    public void insertClosetInfoData() {
        System.out.println("insertClosetInfoData 메소드 호출됨");
        List<ClosetInfoEntity> entities = Arrays.asList(
                new ClosetInfoEntity("T-shirt", "Top", 24, 40),
                new ClosetInfoEntity("Trouser", "Bottom",0, 40),
                new ClosetInfoEntity("Pullover", "Top",-5, 22),
                new ClosetInfoEntity("Dress", "Onepiece",12, 40),
                new ClosetInfoEntity("Coat", "Outer",-5, 16),
                new ClosetInfoEntity("Sandal", "Shoes",23, 40),
                new ClosetInfoEntity("Shirt", "Top",17, 27),
                new ClosetInfoEntity("Sneaker", "Shoes",0, 40),
                new ClosetInfoEntity("Bag", "Bag",0, 40),
                new ClosetInfoEntity("Ankle boot", "Shoes",-5, 11)
        );

        closetInfoRepository.saveAll(entities);
    }
}