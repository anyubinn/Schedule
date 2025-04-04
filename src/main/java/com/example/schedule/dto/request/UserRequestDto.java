package com.example.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 추가 요청에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class UserRequestDto {

    @Size(max = 4, message = "유저명은 4자까지 입력이 가능합니다.")
    @NotBlank(message = "유저명은 필수 입력 값입니다.")
    private final String userName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,63}$", message = "이메일 형식에 맞게 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;
}
