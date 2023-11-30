package com.example.dressing.repository;

import com.example.dressing.entity.SuggestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestRepository extends JpaRepository<SuggestEntity, Long> {
}