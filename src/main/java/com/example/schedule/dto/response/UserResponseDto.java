package com.example.schedule.dto.response;

import com.example.schedule.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private final Long id;

    private final String userName;

    private final String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserResponseDto toDto(User user) {

        return new UserResponseDto(user.getId(), user.getUserName(), user.getEmail(), user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
