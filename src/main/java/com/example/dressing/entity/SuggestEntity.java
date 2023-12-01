package com.example.dressing.entity;

import com.example.dressing.dto.SuggestDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "suggest_table")
public class SuggestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //건의 id

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "id") //실제 테이블에서 fk 이름 "uid", UserEntity에서 참조되는 속성 이름 "id"
    private UserEntity userEntity; //FK, DB에는 UserEntity id 저장

    @Column
    private String title; //건의 제목

    @Column
    private String content; //건의 내용

    // SuggestDTO -> SuggestEntity 변환
    public static SuggestEntity toSuggestEntity(SuggestDTO suggestDTO, UserEntity userEntity) {
        SuggestEntity suggestEntity = new SuggestEntity();
        suggestEntity.setId(suggestDTO.getId());
        suggestEntity.setUserEntity(userEntity);
        suggestEntity.setTitle(suggestDTO.getTitle());
        suggestEntity.setContent(suggestDTO.getContent());

        return suggestEntity;
    }
}