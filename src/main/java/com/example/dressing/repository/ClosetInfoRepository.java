package com.example.dressing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.dressing.entity.ClosetInfoEntity;

public interface ClosetInfoRepository extends JpaRepository<ClosetInfoEntity, Long> {
}
