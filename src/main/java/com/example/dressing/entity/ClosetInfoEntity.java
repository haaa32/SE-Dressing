package com.example.dressing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "closet_info_table")
public class ClosetInfoEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16)
    private String category;

    @Column(length = 16, unique = true)
    private String label; // ClosetEntity에서 fk가 될 예정

    @Column
    private int minTemp;

    @Column
    private int maxTemp;
}
