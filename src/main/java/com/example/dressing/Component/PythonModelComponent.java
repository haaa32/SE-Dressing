package com.example.dressing.Component;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 파이썬 코드를 ProcessBuilder으로 실행 컴포넌트
@Component
public class PythonModelComponent {
    // 이미지를 얻어서 옷 라벨을 반환하는 프로세스
    public String getLabel(String imagePath) throws IOException {
        // 파이썬 스크립트 경로
        String pythonScriptPath = "C:/SE_python/keras_classfy.py";

        // 파이썬 스크립트에 전달할 입력 데이터
//        String inputData = "C:/Users/kbg01/Desktop/스크린샷 2023-11-12 144511.png";

        // 파이썬 스크립트 실행
        ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, imagePath);
        Process process = processBuilder.start();

        // 파이썬 스크립트의 출력을 읽어오기
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String label = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            label = line; // 파이썬 출력결과를 라벨로
        }

        // 프로세스 종료
        try {
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return label; // 라벨 리턴
    }
}