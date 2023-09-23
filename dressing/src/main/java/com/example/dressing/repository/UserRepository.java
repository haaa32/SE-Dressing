package com.example.dressing.repository;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> { //JpaR 인자규칙 <다루는 엔터티, pk 자료형>
    //Repository 객체로 DB와 작업할 땐 반드시 Entity 객체로 오고 간다

    // id로 회원 정보 조회
    // 규칙대로 만들면 스프링 JPA가 쿼리 자동 생성 (select * from user_table where user_id=?)
    Optional<UserEntity> findByUserId(String userId);

}
