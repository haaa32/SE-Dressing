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
        CompletableFuture<String> future = client
                .prepareGet("https://weatherapi-com.p.rapidapi.com/current.json?q=Daegu")
                .setHeader("X-RapidAPI-Key", "862fab4804msh877bf652bb7744ep1629ddjsn7e6cef4000ba")
                .setHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    System.out.println("API Response: " + response.getResponseBody());
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(response.getResponseBody());
                        JsonNode currentWeather = rootNode.path("current");

                        double tempCelsius = currentWeather.path("temp_c").asDouble();
                        String weatherCondition = currentWeather.path("condition").path("text").asText();

                        return "대구 현재 온도: " + tempCelsius + "°C, 날씨 상태: " + weatherCondition;
                    }catch (Exception e) {
                        e.printStackTrace();
                        return "날씨 정보를 파싱하는 데 실패했습니다.";
                    }
                }).whenComplete((result, throwable) -> {
                    try {
                        client.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


        return future;
    }
}
