package com.example.schedule.handler;

import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 로그인된 유저 정보를 전역적으로 처리하는 핸들러 클래스
 */
@ControllerAdvice
@RequiredArgsConstructor
public class LoginUserHandler {

    private final UserRepository userRepository;

    /**
     * 현재 로그인한 사용자의 정보를 검증하고 반환한다.
     *
     * @param request 현재 HTTP 요청 객체
     * @return 로그인된 사용자 정보, 로그인 안 했으면 null
     */
    @ModelAttribute("loginUser")
    private User validateLoggedIn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        String email = (String) session.getAttribute("sessionKey");

        return userRepository.findByEmailOrElseThrow(email);
    }
}
