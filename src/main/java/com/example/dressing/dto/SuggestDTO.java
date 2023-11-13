package com.example.dressing.dto;

import com.example.dressing.entity.SuggestEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SuggestDTO {
    private Long id; //건의 id
    private Long uid; //user의 id ***이거 Entity랑 똑같이 UserEntity로 받아야 할지 아님 그냥 id값만 받게 Long할지 두고보기
    private String userId; //user의 userId 있으면 편할듯!!해서 일단 집어넘
    private String title; //건의 제목
    private String content; //건의 내용

    public static SuggestDTO toSuggestDTO(SuggestEntity suggestEntity) {
        SuggestDTO suggestDTO = new SuggestDTO();
        suggestDTO.setId(suggestEntity.getId());
        suggestDTO.setUid(suggestEntity.getUserEntity().getId()); //***
        suggestDTO.setUserId(suggestEntity.getUserEntity().getUserId()); //***
        suggestDTO.setTitle(suggestEntity.getTitle());
        suggestDTO.setContent(suggestEntity.getContent());

        return suggestDTO;
    }
}