package com.juju.cozyformombackend3.domain.communitylog.comment.dto;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class CommentDto {
	private final Long commentId;
	private final Long parentId;
	private final String comment;
	private final String createdAt;
	private final String updatedAt;
	private final Boolean isDeleted;
	private final Long writerId;
	private final String writerNickname;
	private final String writerImageUrl;
	private List<CommentDto> childComment = new ArrayList<>();

	@QueryProjection
	public CommentDto(Long commentId, Long parentId, String comment, String createdAt, String updatedAt,
		Boolean isDeleted, Long writerId, String writerNickname, String writerImageUrl) {
		this.commentId = commentId;
		this.parentId = parentId;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDeleted = isDeleted;
		this.writerId = writerId;
		this.writerNickname = writerNickname;
		this.writerImageUrl = writerImageUrl;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void addChildComment(CommentDto childComment) {
		this.childComment.add(childComment);
	}
}
