package com.example.dressing.repository;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> { //JpaR 인자규칙 <다루는 엔터티, pk 자료형>
    //Repository 객체로 DB와 작업할 땐 반드시 Entity 객체로 오고 간다

    // id로 회원 정보 조회
    // 규칙대로 만들면 스프링 JPA가 쿼리 자동 생성 (select * from user_table where user_id=?)
    Optional<UserEntity> findByUserId(String userId);

    //@Override
    // 테이블의 모든 객체를 리스트로 반환
    List<UserEntity> findAll();

    // 사용자의 id를 이용해서 user의 rank를 업데이트 (DB update 쿼리)
    @Modifying(clearAutomatically = true) //@Query으로 insert, update, delete를 할 때 무조건적으로 붙여야함
    @Query("UPDATE UserEntity ut SET ut.userRank = :rank where ut.id = :id")
    @Transactional //update, delete 쿼리를 실행할 때 꼭 붙여야함 (TransactionRequiredException)
    int updateUserRank(@Param(value = "rank") String rank, @Param(value = "id") Long id);

}
