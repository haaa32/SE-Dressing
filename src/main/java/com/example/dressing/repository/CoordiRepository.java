package com.example.dressing.repository;

import com.example.dressing.entity.CoordiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordiRepository extends JpaRepository<CoordiEntity, Long> {
}
