package com.example.schedule.service;

import com.example.schedule.config.PasswordEncoder;
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

/**
 * 유저 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입한다.
     *
     * @param dto 등록할 유저 정보를 담고 있는 DTO
     * @return 저장된 유저 정보에 대한 응답 DTO
     */
    public UserResponseDto save(UserRequestDto dto) {

        // 평문 비밀번호를 암호화하여 저장
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUserName(), dto.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    /**
     * 모든 유저를 조회한다.
     *
     * @return 조회된 유저 리스트 정보에 대한 응답 DTO
     */
    public List<UserResponseDto> findAll() {

        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    /**
     * 특정 유저를 조회한다.
     *
     * @param id 조회할 유저의 id
     * @return 조회된 유저 정보에 대한 응답 DTO
     */
    public UserResponseDto findById(Long id) {

        // 유저가 존재하는지 조회
        User findUser = userRepository.findByIdOrElseThrow(id);

        return UserResponseDto.toDto(findUser);
    }

    /**
     * 특정 유저를 수정한다.
     *
     * @param id 수정할 유저의 id
     * @param dto 수정할 유저의 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 수정된 유저 정보에 대한 응답 DTO
     */
    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto dto, User loginUser) {

        // 유저 당사자가 맞는지 확인
        User findUser = validateUserAuth(loginUser, id);
        findUser.updateUser(dto.getUserName());

        return UserResponseDto.toDto(findUser);
    }

    /**
     * 특정 유저를 삭제한다.
     *
     * @param id 삭제할 유저의 id
     * @param loginUser 현재 로그인된 유저 정보
     */
    public void delete(Long id, User loginUser) {

        // 유저 당사자가 맞는지 확인
        User findUser = validateUserAuth(loginUser, id);

        userRepository.delete(findUser);
    }

    /**
     * 유저 수정/삭제 시 유저 권한을 검증한다.
     * @param user 현재 로그인한 유저 정보
     * @param id 일정 id
     * @return 유저 정보, 권한 인증 실패시 403 Forbidden 예외 발생
     */
    private User validateUserAuth(User user, Long id) {

        // 유저가 존재하는지 조회
        User findUser = userRepository.findByIdOrElseThrow(id);

        // 현재 로그인된 유저와 유저 작성자가 다른 경우
        if (findUser.getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 유저만 수정/삭제가 가능합니다.");
        }

        return findUser;
    }
}
