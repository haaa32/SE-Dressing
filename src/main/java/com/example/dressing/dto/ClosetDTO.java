package com.example.dressing.dto;

import lombok.*;

@Getter
@Setter
@ToString // 필드 값 확인할 때 사용함
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class ClosetDTO {
    private Long id;
    private String fileUpload;


}
