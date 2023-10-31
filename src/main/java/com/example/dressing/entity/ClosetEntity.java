package com.example.dressing.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "closet_table")
public class ClosetEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @JoinColumn(name="userId") // fk
    private User user; // 참조할 테이블

    private String label;

    @ManyToOne // Many: closet, One: user - 한명의 유저는 여러개의 옷을 업로드할 수 있음
    private String url;
}
