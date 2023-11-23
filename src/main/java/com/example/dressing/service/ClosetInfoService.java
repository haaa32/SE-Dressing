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
    @PostConstruct
    public void init() {
        insertClosetInfoData();
    }

    @Autowired
    private ClosetInfoRepository closetInfoRepository;

    public void insertClosetInfoData() {
        System.out.println("insertClosetInfoData 메소드 호출됨");
        List<ClosetInfoEntity> entities = Arrays.asList(
                new ClosetInfoEntity("T-shirt/top", 24, 40),
                new ClosetInfoEntity("Trouser", 0, 40),
                new ClosetInfoEntity("Pullover", -5, 22),
                new ClosetInfoEntity("Dress", 12, 40),
                new ClosetInfoEntity("Coat", -5, 16),
                new ClosetInfoEntity("Sandal", 23, 40),
                new ClosetInfoEntity("Shirt", 17, 27),
                new ClosetInfoEntity("Sneaker", 0, 40),
                new ClosetInfoEntity("Bag", 0, 40),
                new ClosetInfoEntity("Ankle boot", -5, 11)
        );

        closetInfoRepository.saveAll(entities);
    }
}
