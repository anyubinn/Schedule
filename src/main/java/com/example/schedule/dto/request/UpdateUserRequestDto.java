package com.example.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {

    private final String userName;

    private final String email;

    private final String oldPassword;

    private final String newPassword;
}
