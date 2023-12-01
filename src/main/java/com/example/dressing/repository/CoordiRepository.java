package com.example.dressing.repository;

import com.example.dressing.entity.CoordiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordiRepository extends JpaRepository<CoordiEntity, Long> {
    // Uesr의 id와 Cooldi의 heart로 찾기
    @Query("SELECT c FROM CoordiEntity c WHERE c.userEntity.id = :uid and c.heart = :heart")
    List<CoordiEntity> findUserCoordisByHeart(@Param("uid") Long uid, @Param("heart") int heart);
}