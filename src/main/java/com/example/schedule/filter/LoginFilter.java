package com.example.schedule.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

/**
 * 로그인 상태를 검증하는 필터
 */
@Slf4j
public class LoginFilter implements Filter {

    // 인증이 필요하지 않은 경로 리스트
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/login", "/logout"};

    /**
     * 필터를 실행하여 로그인 여부를 확인한다.
     *
     * @param servletRequest 클라이언트의 요청
     * @param servletResponse 서버의 응답
     * @param filterChain 필터 체인
     * @throws IOException 입출력 예외 발생 시
     * @throws ServletException 서블릿 예외 발생 시
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        log.info("로그인 필터 로직 실행");

        // WHITE_LIST에 포함되지 않은 경우
        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("sessionKey") == null) {
                throw new RuntimeException("로그인 해주세요.");
            }

            log.info("로그인에 성공했습니다.");
        }

        // 다음 필터로 요청을 전달
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 요청 URI가 WHITE_LIST에 포함되는지 확인한다.
     *
     * @param requestURI 클라이언트의 요청 URI
     * @return WHITE_LIST에 포함되면 true, 아니면 false
     */
    private boolean isWhiteList(String requestURI) {

        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
