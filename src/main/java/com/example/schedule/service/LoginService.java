package com.example.schedule.service;

import com.example.schedule.config.PasswordEncoder;
import com.example.schedule.dto.request.LoginRequestDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * 로그인/로그아웃 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인한다.
     *
     * @param dto 로그인할 사용자 정보를 담고 있는 DTO
     * @param request 클라이언트의 HTTP 요청 정보
     */
    public void login(LoginRequestDto dto, HttpServletRequest request) {

        // 유저가 존재하는지 조회
        User findUser = userRepository.findByEmailOrElseThrow(dto.getEmail());

        // 입력한 비밀번호가 유저의 비밀번호와 틀린 경우
        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("sessionKey", dto.getEmail());
    }

    /**
     * 로그아웃한다.
     *
     * @param request 클라이언트의 HTTP 요청 정보
     */
    public void logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }
}
