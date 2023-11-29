//Entity: 일종의 table 역할
//spring data jpa: DB의 table을 자바 객체처럼 사용하도록 한다
package com.example.dressing.entity;

import com.example.dressing.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DynamicInsert //Column default를 처음에 DB에 저장할 때, 실제로 디폴트값이 저장되지 않는데, 이는 엔터티 자체가 null이 아니기 때문이다. 애노테이션은 엔티티를 save할 때 null 값은 배제하고 insert 쿼리를 날리도록 한다.
@Table(name = "user_table") //name: DB에 생성될 때 테이블 이
public class UserEntity extends TimeEntity { //엔티티 클래스 대로 실제로 DB에 테이블이 생성되게 된다
    @Id //pk 지정(primary key(주키))
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql에서 auto_increment, 오라클의 sequence
    private Long id;

    @Column //unique 디폴트 false (중복 허용)
    private String userName;

    @Column(unique = true) //unique 제약조건 추가
    private String userId;

    @Column(length = 16) //열 길이도 지정 가능
    private String userPassword;

    @Column(length = 13) //열 길이 13
    private String phoneNumber;

    @Column(length = 10)
    @ColumnDefault("'Bronze'") //default 값 설정
    private String userRank;

    //회원가입 날짜 엔터티는 TimeEntity 상속

    @Column
    @ColumnDefault("'Daegu'")
    private String local; //대구 고정

    // 사용자가 추천받기 버튼을 몇 번 눌렀는지
    @Column
    @ColumnDefault("0")
    private Integer numUserCoordi = 0;

    //유저 DTO를 유저 Entity로 반환 (JpaRepository 를 사용하기 위해)
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setUserPassword(userDTO.getUserPassword());

        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setUserRank(userDTO.getUserRank());
        userEntity.setCreatedDate(userDTO.getCreatedDate());
        userEntity.setLocal(userDTO.getLocal());
        userEntity.setNumUserCoordi(userDTO.getNumUserCoordi());

        return userEntity;
    }


    //회원 업데이트에서 사용하는 유저 DTO -> 유저 Entity ...why? 회원 업데이트에선 id가 무조건 있기 때문에!! (회원가입시에는 아직 id가 정해지지 않음)
    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId()); //추가!!
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setUserPassword(userDTO.getUserPassword());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setUserRank(userDTO.getUserRank());
        userEntity.setCreatedDate(userDTO.getCreatedDate());
        userEntity.setLocal(userDTO.getLocal());
        userEntity.setNumUserCoordi(userDTO.getNumUserCoordi());

        return userEntity;
    }
}
