package com.juju.cozyformombackend3.domain.communitylog.comment.dto.request;

import lombok.Getter;

@Getter
public class ModifyCommentRequest {
	private Long commentId;
	private Long parentId;
	private String comment;
}
