package com.example.dressing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Scheduled 어노테이션을 사용하기 위해 다음과 같이 Application Class에 @EnableScheduling을 추가
@SpringBootApplication
public class DressingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DressingApplication.class, args);
	}

}