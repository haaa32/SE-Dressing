package com.example.dressing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "closet_info_table")
@NoArgsConstructor // Lombok을 사용하여 기본 생성자 추가
public class ClosetInfoEntity {
    @Id
    @Column(length = 16)
    private String label; // ClosetEntity에서 fk가 될 예정

    @Column(length = 16)
    private String category;

    @Column
    private int minTemp;

    @Column
    private int maxTemp;

    // ClosetInfoEntity 클래스의 생성자
    // 옷장 항목에 대한 정보를 초기화하는 데 사용
    public ClosetInfoEntity(String label, String category, int minTemp, int maxTemp) {
        this.label = label;
        this.category = category;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }
}
