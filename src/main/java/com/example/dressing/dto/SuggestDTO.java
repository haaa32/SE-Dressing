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
    private Long uid;
    private String userId;
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