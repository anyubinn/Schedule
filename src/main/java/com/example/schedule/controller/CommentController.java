package com.example.schedule.controller;

import com.example.schedule.dto.request.CommentRequestDto;
import com.example.schedule.dto.request.UpdateCommentRequestDto;
import com.example.schedule.dto.response.CommentResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@PathVariable Long scheduleId, @RequestBody CommentRequestDto dto,
                                                   @ModelAttribute("loginUser") User loginUser) {

        return new ResponseEntity<>(commentService.save(scheduleId, dto, loginUser), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(@PathVariable Long scheduleId,
                                                            @RequestParam(required = false) String userName) {

        return ResponseEntity.ok(commentService.findAll(scheduleId, userName));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long scheduleId, @PathVariable Long commentId) {

        return ResponseEntity.ok(commentService.findById(scheduleId, commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId,
                                                            @RequestBody UpdateCommentRequestDto dto,
                                                            @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.ok(commentService.updateComment(scheduleId, commentId, dto, loginUser));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId, @PathVariable Long commentId,
                                       @ModelAttribute("loginUser") User loginUser) {

        commentService.delete(scheduleId, commentId, loginUser);

        return ResponseEntity.ok().build();
    }
}
