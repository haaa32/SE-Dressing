package com.example.dressing.repository;

import com.example.dressing.entity.ClosetInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClosetInfoRepository extends JpaRepository<ClosetInfoEntity, Long> {
    // label로 찾기
    @Query("SELECT ci FROM ClosetInfoEntity ci WHERE ci.label = :label")
    Optional<ClosetInfoEntity> findByLabel(@Param("label") String label);
}
