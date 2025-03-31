package com.example.schedule.service;

import com.example.schedule.dto.request.DeleteRequestDto;
import com.example.schedule.dto.request.UpdateUserRequestDto;
import com.example.schedule.dto.request.UserRequestDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto save(UserRequestDto dto) {

        User user = new User(dto.getUserName(), dto.getEmail(), dto.getPassword());
        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    public List<UserResponseDto> findAll() {

        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    public UserResponseDto findById(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        return UserResponseDto.toDto(findUser);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto dto) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!findUser.getPassword().equals(dto.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findUser.updateUser(dto.getUserName(), dto.getEmail(), dto.getNewPassword());

        return UserResponseDto.toDto(findUser);
    }

    public void delete(Long id, DeleteRequestDto dto) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!findUser.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(findUser);
    }
}
