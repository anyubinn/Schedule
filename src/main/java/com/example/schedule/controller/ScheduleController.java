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

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@Valid @RequestBody ScheduleRequestDto dto,
                                                    @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(dto, loginUser));
    }

    @GetMapping
    public ResponseEntity<Page<ReadScheduleResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(required = false) String userName) {

        Sort sort = Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(scheduleService.findAll(userName, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadScheduleResponseDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                                              @RequestBody UpdateScheduleRequestDto dto,
                                                              @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.ok(scheduleService.updateSchedule(id, dto, loginUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @ModelAttribute("loginUser") User loginUser) {

        scheduleService.delete(id, loginUser);

        return ResponseEntity.ok().build();
    }
}
