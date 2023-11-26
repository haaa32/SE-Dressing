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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "id") // fk
    private UserEntity userEntity;

    @Column
    private Long outerId;

    @Column
    private Long topId;

    @Column
    private Long bottomId;

    @Column
    private Long shoesId;

    @Column
    private Long bagId;

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
