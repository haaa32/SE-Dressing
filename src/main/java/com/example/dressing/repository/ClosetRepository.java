package com.example.dressing.repository;

import com.example.dressing.entity.ClosetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClosetRepository extends JpaRepository<ClosetEntity, Long> {
    @Query("SELECT c FROM ClosetEntity c WHERE c.userEntity.id = :uid")
    List<ClosetEntity> findUserPhotos(@Param("uid") Long uid);

    @Query("SELECT c FROM ClosetEntity c WHERE c.userEntity.id = :uid and c.closetInfoEntity.category = :category")
    List<ClosetEntity> findUserPhotosByCategory(@Param("uid") Long uid, @Param("category") String category);
}
