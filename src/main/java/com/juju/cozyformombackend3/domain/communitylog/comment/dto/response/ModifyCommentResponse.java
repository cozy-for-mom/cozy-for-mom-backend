package com.juju.cozyformombackend3.domain.communitylog.comment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModifyCommentResponse {
	private final Long commentId;

	public static ModifyCommentResponse of(Long commentId) {
		return new ModifyCommentResponse(commentId);
	}
}
