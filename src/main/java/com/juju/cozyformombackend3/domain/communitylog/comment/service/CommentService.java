package com.juju.cozyformombackend3.domain.communitylog.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.comment.dto.request.CreateCommentRequest;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.request.ModifyCommentRequest;
import com.juju.cozyformombackend3.domain.communitylog.comment.error.CommentErrorCode;
import com.juju.cozyformombackend3.domain.communitylog.comment.model.Comment;
import com.juju.cozyformombackend3.domain.communitylog.comment.repository.CommentRepository;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.error.CozyLogErrorCode;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.repository.CozyLogRepository;
import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	private final UserRepository userRepository;
	private final CozyLogRepository cozyLogRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public Long createComment(Long userId, Long cozyLogId, CreateCommentRequest request) {
		CozyLog foundCozyLog = cozyLogRepository.findById(cozyLogId)
			.orElseThrow(() -> new BusinessException(CozyLogErrorCode.NOT_FOUND_COZY_LOG));
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
		foundCozyLog.addComment(request.toEntity(writer));

		return foundCozyLog.getId();
	}

	@Transactional
	public Long updateComment(Long userId, ModifyCommentRequest request) {
		Comment foundComment = commentRepository.findById(request.getCommentId())
			.orElseThrow(() -> new BusinessException(CommentErrorCode.NOT_FOUND_COMMENT));
		foundComment.update(request.getComment());

		return foundComment.getId();
	}
}
