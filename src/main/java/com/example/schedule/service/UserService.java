package com.example.schedule.service;

import com.example.schedule.dto.request.UserRequestDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto save(UserRequestDto dto) {

        User user = new User(dto.getUserName(), dto.getEmail());
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
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        User findUser = userRepository.findByIdOrElseThrow(id);
        findUser.updateUser(dto.getUserName(), dto.getEmail());

        return UserResponseDto.toDto(findUser);
    }

    public void delete(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(findUser);
    }
}
