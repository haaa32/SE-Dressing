package com.example.dressing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "closet_info_table")
@NoArgsConstructor // Lombok을 사용하여 기본 생성자 추가
public class ClosetInfoEntity {
    @Id
    @Column(length = 16)
    private String label; // ClosetEntity에서 fk가 될 예정

    @OneToMany(mappedBy = "closetInfoEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClosetEntity> closets = new ArrayList<>();

    @Column(length = 16)
    private String category; // 옷 카테고리

    @Column
    private int minTemp; // 최소 온도

    @Column
    private int maxTemp; //최대 온도

    // ClosetInfoEntity 클래스의 생성자
    // 옷장 항목에 대한 정보를 초기화하는 데 사용
    public ClosetInfoEntity(String label, String category, int minTemp, int maxTemp) {
        this.label = label;
        this.category = category;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }
}