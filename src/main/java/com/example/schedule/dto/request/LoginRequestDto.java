package com.example.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 요청에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,63}$", message = "이메일 형식에 맞게 입력해주세요.")
    @NotBlank(message = "로그인할 이메일을 입력해주세요.")
    private final String email;

    @NotBlank(message = "로그인할 비밀번호를 입력해주세요.")
    private final String password;
}
