package com.example.dressing.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "coordi_table")
@NoArgsConstructor
public class CoordiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cooldi id

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "id") // fk
    private UserEntity userEntity; //User id

    @Column
    private Long outerId; // 아우터 id

    @Column
    private Long topId; //상의 id

    @Column
    private Long bottomId; // 하의 id

    @Column
    private Long shoesId; // 신발 id

    @Column
    private Long bagId; // 가방 id

    @Column
    private int heart; //좋아요: 1, 싫어요: -1

    @Builder
    public CoordiEntity(UserEntity userEntity, Long outerId, Long topId, Long bottomId, Long shoesId, Long bagId, int heart) {
        this.id = id;
        this.userEntity = userEntity;
        this.outerId = outerId;
        this.topId = topId;
        this.bottomId = bottomId;
        this.shoesId = shoesId;
        this.bagId = bagId;
        this.heart = heart;
    }
}