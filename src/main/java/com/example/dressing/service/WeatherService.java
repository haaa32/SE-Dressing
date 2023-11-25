package com.example.dressing.service;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {
    public CompletableFuture<String> getDaeguWeather() {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        // 비동기 HTTP 클라이언트를 생성
        CompletableFuture<String> future = client
                .prepareGet("https://weatherapi-com.p.rapidapi.com/current.json?q=Daegu")
                .setHeader("X-RapidAPI-Key", "862fab4804msh877bf652bb7744ep1629ddjsn7e6cef4000ba")
                .setHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .execute() // API 요청을 실행
                .toCompletableFuture()
                .thenApply(response -> {    // 응답을 처리하는 람다 함수
                    System.out.println("API Response: " + response.getResponseBody());
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();     // JSON 처리를 위한 ObjectMapper
                        JsonNode rootNode = objectMapper.readTree(response.getResponseBody());  // JSON 응답을 파싱
                        JsonNode currentWeather = rootNode.path("current");         // 현재 날씨 정보를 추출

                        // 온도와 날씨 상태를 추출
                        double tempCelsius = currentWeather.path("temp_c").asDouble();
                        String weatherCondition = currentWeather.path("condition").path("text").asText();

                        // 추출된 정보를 반환
                        return "대구 현재 온도: " + tempCelsius + "°C, 날씨 상태: " + weatherCondition;
                    }catch (Exception e) {
                        e.printStackTrace();
                        return "날씨 정보를 파싱하는 데 실패했습니다.";
                    }
                }).whenComplete((result, throwable) -> {    // 작업 완료 후 클라이언트를 닫습니다.
                    try {
                        client.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


        return future;
    }
}