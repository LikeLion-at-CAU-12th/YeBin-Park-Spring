package com.lionyebin.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/healthCheck")//이 클래스의 모든 경로가 여기 매핑
public class HealthCheck {
    @GetMapping("/1")//GET 요청 처리
    public Map<String, String> healthCheck() { //Map<string, string>타입으로 변환-> JSON 객체로 변환가능
        Map<String, String> response = new HashMap<>();
        response.put("status", "1isOK");

        return response;
    }
}
