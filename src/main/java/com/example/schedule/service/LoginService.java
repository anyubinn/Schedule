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

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void login(LoginRequestDto dto, HttpServletRequest request) {

        User findUser = userRepository.findByEmailOrElseThrow(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("sessionKey", dto.getEmail());
    }

    public void logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }
}
