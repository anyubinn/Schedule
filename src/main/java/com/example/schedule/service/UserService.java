package com.example.schedule.service;

import com.example.schedule.config.PasswordEncoder;
import com.example.schedule.dto.request.UpdateUserRequestDto;
import com.example.schedule.dto.request.UserRequestDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto save(UserRequestDto dto) {

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUserName(), dto.getEmail(), encodedPassword);
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
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto dto, HttpServletRequest request) {

        User loginUser = validateLoggedIn(request);
        User findUser = validateUserAuth(loginUser, id);

        findUser.updateUser(dto.getUserName());

        return UserResponseDto.toDto(findUser);
    }

    public void delete(Long id, HttpServletRequest request) {

        User loginUser = validateLoggedIn(request);
        User findUser = validateUserAuth(loginUser, id);

        userRepository.delete(findUser);
    }

    private User validateLoggedIn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("sessionKey");

        return userRepository.findByEmailOrElseThrow(email);
    }

    private User validateUserAuth(User user, Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if (findUser.getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 유저만 수정/삭제가 가능합니다.");
        }

        return findUser;
    }
}
