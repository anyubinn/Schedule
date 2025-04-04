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

/**
 * 댓글의 CRUD를 담당하는 컨트롤러
 */
@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 특정 일정에 대한 새로운 댓글을 추가한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param dto 추가할 댓글 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 생성된 댓글 정보와 HTTP 상태(201 CREATED)
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@PathVariable Long scheduleId, @RequestBody CommentRequestDto dto,
                                                   @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, dto, loginUser));
    }

    /**
     * 특정 일정에 대한 모든 댓글을 조회한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param userName 검색 기준인 유저명, 필수가 아님
     * @return 조회된 댓글 리스트 정보와 HTTP 상태(200 OK)
     */
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(@PathVariable Long scheduleId,
                                                            @RequestParam(required = false) String userName) {

        return ResponseEntity.ok(commentService.findAll(scheduleId, userName));
    }

    /**
     * 특정 일정에 대한 특정 댓글을 조회한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param commentId 조회할 댓글 id
     * @return 조회된 댓글 정보와 HTTP 상태(200 OK)
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long scheduleId, @PathVariable Long commentId) {

        return ResponseEntity.ok(commentService.findById(scheduleId, commentId));
    }

    /**
     * 특정 일정에 대한 특정 댓글을 수정한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param commentId 수정할 댓글 id
     * @param dto 수정할 댓글 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 수정된 유저 정보와 HTTP 상태(200 OK)
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId,
                                                            @RequestBody UpdateCommentRequestDto dto,
                                                            @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.ok(commentService.updateComment(scheduleId, commentId, dto, loginUser));
    }

    /**
     * 특정 일정에 대한 특정 댓글을 삭제한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param commentId 삭제할 댓글 id
     * @param loginUser 현재 로그인된 유저 정보
     * @return 삭제 성공 시 HTTP 상태(200 OK)
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId, @PathVariable Long commentId,
                                       @ModelAttribute("loginUser") User loginUser) {

        commentService.delete(scheduleId, commentId, loginUser);

        return ResponseEntity.ok().build();
    }
}
