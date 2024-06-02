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

import com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto.CreateComment;
import com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto.FindCommentList;
import com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto.ModifyComment;
import com.juju.cozyformombackend3.domain.communitylog.comment.service.CommentService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = CreateComment.Request.class)))
        @RequestBody @Valid CreateComment.Request request) {
        CreateComment.Response response = commentService.createComment(userId, cozyLogId, request);
        URI location = URI.create("/api/v1/cozy-log/" + cozyLogId + "/comment/" + response.getCommentId());

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyComment(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long cozyLogId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = ModifyComment.Request.class)))
        @RequestBody @Valid ModifyComment.Request request) {
        Long modifiedCommentId = commentService.updateComment(userId, request);

        return ResponseEntity.ok(SuccessResponse.of(200, ModifyComment.Response.of(modifiedCommentId)));
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
        FindCommentList.Response response = commentService.findCommentList(cozyLogId);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
