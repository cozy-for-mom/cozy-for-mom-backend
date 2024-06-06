package com.juju.cozyformombackend3.domain.communitylog.comment.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto.CreateComment;
import com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto.FindCommentList;
import com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto.ModifyComment;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.CommentDto;
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
    public CreateComment.Response createComment(Long userId, Long cozyLogId, CreateComment.Request request) {
        CozyLog foundCozyLog = cozyLogRepository.findById(cozyLogId)
            .orElseThrow(() -> new BusinessException(CozyLogErrorCode.NOT_FOUND_COZY_LOG));
        User writer = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
        Comment savedComment = commentRepository.save(request.toEntity(writer));
        foundCozyLog.addComment(savedComment);
        if (Objects.isNull(request.getParentId())) {
            savedComment.isParentComment();
        }

        return CreateComment.Response.of(savedComment.getId());
    }

    @Transactional
    public Long updateComment(Long userId, ModifyComment.Request request) {
        Comment foundComment = commentRepository.findById(request.getCommentId())
            .orElseThrow(() -> new BusinessException(CommentErrorCode.NOT_FOUND_COMMENT));
        foundComment.update(request.getComment());

        return foundComment.getId();
    }

    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment foundComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new BusinessException(CommentErrorCode.NOT_FOUND_COMMENT));
        foundComment.delete();
    }

    public FindCommentList.Response findCommentList(Long cozyLogId) {
        List<CommentDto> commentList = commentRepository.findAllByCozyLogId(cozyLogId);
        Map<Long, CommentDto> response = commentList.stream()
            .filter(comment -> comment.getCommentId().equals(comment.getParentId()))
            .collect(Collectors.toMap(CommentDto::getCommentId, Function.identity()));
        commentList.forEach(comment -> {
            if (Objects.nonNull(comment.getParentId()) &&
                !Objects.equals(comment.getCommentId(), comment.getParentId())) {
                response.get(comment.getParentId()).addChildComment(comment);
            }
        });
        return FindCommentList.Response.of(response.values().stream().toList());
    }
}
