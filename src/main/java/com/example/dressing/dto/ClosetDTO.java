package com.example.dressing.dto;

import com.example.dressing.entity.ClosetEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClosetDTO {
    private Long id; //옷 id (pk)
    private Long uid; //user의 id (fk)
    private String label; // ClosetInfo의 label
    private String orgNm;
    private String savedNm;
    private String savedPath;

    public static ClosetDTO toClosetDTO(ClosetEntity closetEntity) {
        ClosetDTO closetDTO = new ClosetDTO();
        closetDTO.setId(closetEntity.getId());
        closetDTO.setId(closetEntity.getUserEntity().getId());
        closetDTO.setLabel(closetEntity.getClosetInfoEntity().getLabel());
        closetDTO.setOrgNm(closetEntity.getOrgNm());
        closetDTO.setSavedNm(closetEntity.getSavedNm());
        closetDTO.setSavedPath(closetEntity.getSavedPath());

        return closetDTO;
    }
}