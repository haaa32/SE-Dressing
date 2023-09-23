//Entity: 일종의 table 역할
//spring data jpa: DB의 table을 자바 객체처럼 사용하도록 한다
package com.example.dressing.entity;

import com.example.dressing.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_table") //name: DB에 생성될 때 테이블 이
public class UserEntity { //엔티티 클래스 대로 실제로 DB에 테이블이 생성되게 된다
    @Id //pk 지정(primary key(주키))
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql에서 auto_increment, 오라클의 sequence (지절로 id가 자동생성 되는듯???)
    private Long id;

    @Column //unique 디폴트 false (중복 허용)
    private String userName;

    @Column(unique = true) //unique 제약조건 추가
    private String userId;

    @Column //열 길이도 지정 가능
    private String userPassword;

    //유저 DTO를 유저 Entity로 반환 (JpaRepository 를 사용하기 위해)
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setUserPassword(userDTO.getUserPassword());

        return userEntity;
    }

    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setUserPassword(userDTO.getUserPassword());

        return userEntity;
    }
}
