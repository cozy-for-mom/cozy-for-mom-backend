package com.juju.cozyformombackend3.domain.communitylog.comment.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.comment.dto.request.CreateCommentRequest;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.request.ModifyCommentRequest;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.response.CreateCommentResponse;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.response.FindCommentListResponse;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.response.ModifyCommentResponse;
import com.juju.cozyformombackend3.domain.communitylog.comment.service.CommentService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cozy-log/{id}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createComment(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long cozyLogId,
        @RequestBody @Valid CreateCommentRequest request) {
        CreateCommentResponse response = commentService.createComment(userId, cozyLogId, request);
        URI location = URI.create("/api/v1/cozy-log/" + cozyLogId + "/comment/" + response.getCommentId());

        return ResponseEntity.created(location)
            .body(SuccessResponse.of(201, response));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyComment(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long cozyLogId,
        @RequestBody @Valid ModifyCommentRequest request) {
        Long modifiedCommentId = commentService.updateComment(userId, request);

        return ResponseEntity.ok(SuccessResponse.of(200, ModifyCommentResponse.of(modifiedCommentId)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> removeComment(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long cozyLogId,
        @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(userId, commentId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getCommentList(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long cozyLogId) {
        FindCommentListResponse response = commentService.findCommentList(cozyLogId);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
