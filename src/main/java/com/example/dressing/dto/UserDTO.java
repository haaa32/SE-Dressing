//DTO: 클래스에 저장될 것들을 필드로 정리 (DB에 저장되는 것?)
package com.example.dressing.dto;

import com.example.dressing.entity.UserEntity;
import lombok.*;

//lombok 라이브러리가 제공해주는 어노테이션
@Getter //getter 자동 만들어줌
@Setter
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 변수를 포함한 생성자
@ToString //toString 자동 생성
public class UserDTO {
    //필드는 모두 private
    private Long id;
    private String userName;
    private String userId;
    private String userPassword;
    private String phoneNumber;


    //UserEntity -> UserDTO 변환
    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        return userDTO;
    }
}
