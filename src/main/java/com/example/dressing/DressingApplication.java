package com.example.dressing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Scheduled ������̼��� ����ϱ� ���� ������ ���� Application Class�� @EnableScheduling�� �߰�
@SpringBootApplication
public class DressingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DressingApplication.class, args);
	}

}