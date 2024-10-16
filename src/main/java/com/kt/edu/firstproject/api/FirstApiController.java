package com.kt.edu.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Json을 반환! / 일반 controller는 보통 html, rest controller는 json 반환
public class FirstApiController {
    @GetMapping("/api/hello")
    public String hello() {
        return "hello world!";
    }
}
