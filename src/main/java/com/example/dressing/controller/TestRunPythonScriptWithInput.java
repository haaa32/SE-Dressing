package com.example.dressing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class TestRunPythonScriptWithInput {
    @GetMapping("testPB")
    public String pls() throws IOException {
        // 파이썬 스크립트 경로
        String pythonScriptPath = "C:/ex.py";

        // 파이썬 스크립트에 전달할 입력 데이터
        String inputData = "C:/6723612710_1_1_3.jpg";

        // 파이썬 스크립트 실행
        ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, inputData);
        Process process = processBuilder.start();

        // 파이썬 스크립트의 출력을 읽어오기
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // 프로세스 종료
        try {
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "main";
    }
}