package com.juju.cozyformombackend3.domain.communitylog.comment.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
	NOT_FOUND_COMMENT(404, "해당 댓글을 찾을 수 없습니다."),
	;;

	private final int status;
	private final String message;

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
