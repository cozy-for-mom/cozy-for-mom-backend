package com.juju.cozyformombackend3.domain.communitylog.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.comment.dto.request.CreateCommentRequest;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.request.ModifyCommentRequest;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.response.CreateCommentResponse;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.response.ModifyCommentResponse;
import com.juju.cozyformombackend3.domain.communitylog.comment.service.CommentService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cozy-log/{id}/comment")
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<SuccessResponse> createComment(@LoginUserId Long userId,
		@PathVariable(name = "id") Long cozyLogId, @RequestBody CreateCommentRequest request) {
		Long createdCommentId = commentService.createComment(userId, cozyLogId, request);
		// TODO: Created 로 바꾸기 ok 가 아니라
		return ResponseEntity.ok(SuccessResponse.of(201, CreateCommentResponse.of(createdCommentId)));
	}

	@PutMapping
	public ResponseEntity<SuccessResponse> modifyComment(@LoginUserId Long userId,
		@PathVariable(name = "id") Long cozyLogId, @RequestBody ModifyCommentRequest request) {
		Long modifiedCommentId = commentService.updateComment(userId, request);

		return ResponseEntity.ok(SuccessResponse.of(200, ModifyCommentResponse.of(modifiedCommentId)));
	}
}
