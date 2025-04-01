package com.example.schedule.controller;

import com.example.schedule.dto.request.LoginRequestDto;
import com.example.schedule.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto dto, HttpServletRequest httpServletRequest) {

        loginService.login(dto, httpServletRequest);

        return ResponseEntity.ok().build();
    }
}
