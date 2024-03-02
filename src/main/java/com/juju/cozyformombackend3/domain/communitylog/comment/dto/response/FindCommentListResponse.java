package com.juju.cozyformombackend3.domain.communitylog.comment.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.comment.dto.CommentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindCommentListResponse {
	private final List<CommentDto> comments;

	public static FindCommentListResponse of(List<CommentDto> comments) {
		return new FindCommentListResponse(comments);
	}
}
