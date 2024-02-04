package com.juju.cozyformombackend3.domain.communitylog.comment.dto.request;

import com.juju.cozyformombackend3.domain.communitylog.comment.model.Comment;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

	private Long parentId;
	private String comment;

	public Comment toEntity(User writer) {
		return Comment.of(comment, parentId, writer, null);
	}
}
