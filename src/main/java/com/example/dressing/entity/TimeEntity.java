package com.example.dressing.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// time 을 속성으로 갖는 엔터티 모음
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TimeEntity {
    @CreationTimestamp //만들어질 때 날짜 자동 저장
    @Column(updatable = false) //업데이트 때는 날짜 변경 안됨
    private LocalDateTime createdDate;

}
