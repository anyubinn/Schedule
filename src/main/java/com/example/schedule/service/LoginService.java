package com.example.schedule.service;

import com.example.schedule.dto.request.LoginRequestDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public boolean login(LoginRequestDto dto, HttpServletRequest request) {

        User findUser = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (findUser == null) {
            return false;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("sessionKey", dto.getEmail());

        return true;
    }
}
