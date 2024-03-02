package com.juju.cozyformombackend3.domain.communitylog.comment.repository;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.comment.dto.CommentDto;

public interface CustomCommentRepository {
	List<CommentDto> findAllByCozyLogId(Long cozyLogId);
}
