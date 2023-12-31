package com.example.dressing.entity;

import com.example.dressing.dto.ClosetDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne // User 테이블과의 관계를 표현하기 위해 @ManyToOne 사용
    @JoinColumn(name = "uid", referencedColumnName = "id") // fk
    private UserEntity userEntity; // UserEntity 클래스와 연결되는 엔티티를 참조

    //    @Column(length = 16)
//    private String label;
    @ManyToOne//(cascade = CascadeType.ALL) // 또는 CascadeType.ALL을 사용하여 모든 작업에 대해 Cascade 설정
    @JoinColumn(name = "label", referencedColumnName = "label")
    private ClosetInfoEntity closetInfoEntity;

    private String orgNm;

    private String savedNm;

    private String savedPath;

    public ClosetEntity() {}

    @Builder
    public ClosetEntity(Long id, UserEntity user, ClosetInfoEntity closetInfoEntity, String label, String orgNm, String savedNm, String savedPath) {
        this.id = id;
        this.userEntity = user;
        this.closetInfoEntity = closetInfoEntity;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
    }

    // ClosetDTO -> ClosetEntity
    public static ClosetEntity toClosetEntity(ClosetDTO closetDTO, UserEntity userEntity, ClosetInfoEntity closetInfoEntity) {
        ClosetEntity closetEntity = ClosetEntity.builder()
                .id(closetDTO.getId())
                .user(userEntity)
                .closetInfoEntity(closetInfoEntity)
                .orgNm(closetDTO.getOrgNm())
                .savedNm(closetDTO.getSavedNm())
                .savedPath(closetDTO.getSavedPath())
                .build();

        return closetEntity;
    }
}