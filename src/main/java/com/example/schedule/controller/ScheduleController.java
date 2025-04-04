package com.example.schedule.controller;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.schedule.dto.response.ReadScheduleResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일정의 CRUD를 담당하는 컨트롤러
 */
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 새로운 일정을 추가한다.
     *
     * @param dto 추가할 일정 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 생성된 일정 정보와 HTTP 상태(201 CREATED)
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@Valid @RequestBody ScheduleRequestDto dto,
                                                    @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(dto, loginUser));
    }

    /**
     * 모든 일정을 조회한다.
     *
     * @param page 조회할 페이지 번호
     * @param size 한 페이지에 포함될 일정 수
     * @param userName 검색 기준인 유저명, 필수가 아님
     * @return 조회된 일정 리스트 정보와 HTTP 상태(200 OK)
     */
    @GetMapping
    public ResponseEntity<Page<ReadScheduleResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(required = false) String userName) {

        // 수정일을 기준으로 내림차순 정렬
        Sort sort = Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(scheduleService.findAll(userName, pageable));
    }

    /**
     * 특정 일정을 조회한다.
     *
     * @param id 조회할 일정의 id
     * @return 조회된 일정 정보와 HTTP 상태(200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReadScheduleResponseDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(scheduleService.findById(id));
    }

    /**
     * 특정 일정을 수정한다.
     *
     * @param id 수정할 일정의 id
     * @param dto 수정할 일정 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 수정된 일정 정보와 HTTP 상태(200 OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                                              @RequestBody UpdateScheduleRequestDto dto,
                                                              @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.ok(scheduleService.updateSchedule(id, dto, loginUser));
    }

    /**
     * 특정 일정을 삭제한다.
     *
     * @param id 삭제할 일정의 id
     * @param loginUser 현재 로그인된 유저 정보
     * @return 삭제 성공 시 HTTP 상태(200 OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @ModelAttribute("loginUser") User loginUser) {

        scheduleService.delete(id, loginUser);

        return ResponseEntity.ok().build();
    }
}
