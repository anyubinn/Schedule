package com.example.schedule.controller;

import com.example.schedule.dto.request.LoginRequestDto;
import com.example.schedule.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 및 로그아웃을 담당하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인한다.
     *
     * @param dto 로그인할 사용자 정보를 담고 있는 DTO
     * @param request 클라이언트의 HTTP 요청 정보
     * @return 로그인 성공 시 HTTP 상태(200 OK)
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto dto, HttpServletRequest request) {

        loginService.login(dto, request);

        return ResponseEntity.ok().build();
    }

    /**
     * 로그아웃한다.
     *
     * @param request 클라이언트의 HTTP 요청 정보
     * @return 로그아웃 성공 시 HTTP 상태(200 OK)
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        loginService.logout(request);

        return ResponseEntity.ok().build();
    }
}
