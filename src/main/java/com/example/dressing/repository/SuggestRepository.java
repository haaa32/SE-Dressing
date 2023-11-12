package com.example.dressing.repository;

import com.example.dressing.entity.SuggestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestRepository extends JpaRepository<SuggestEntity, Long> {
}