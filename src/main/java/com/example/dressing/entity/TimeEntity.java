package com.example.dressing.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// time �� �Ӽ����� ���� ����Ƽ ����
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
//@Setter //���� �߰�..�ٵ� ��� �ɵ�?
public class TimeEntity {
    @CreationTimestamp //������� �� ��¥
    @Column(updatable = false) //������Ʈ ���� ��¥ ���� �ȵ�
    private LocalDateTime createdDate;

}
