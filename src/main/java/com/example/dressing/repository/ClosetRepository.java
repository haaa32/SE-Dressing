package com.example.dressing.repository;

import com.example.dressing.entity.ClosetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosetRepository extends JpaRepository<ClosetEntity, Long> {
}
