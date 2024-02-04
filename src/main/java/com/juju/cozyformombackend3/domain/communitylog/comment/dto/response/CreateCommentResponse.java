package com.juju.cozyformombackend3.domain.communitylog.comment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {

	private final Long commentId;

	public static CreateCommentResponse of(Long commentId) {
		return new CreateCommentResponse(commentId);
	}
}
