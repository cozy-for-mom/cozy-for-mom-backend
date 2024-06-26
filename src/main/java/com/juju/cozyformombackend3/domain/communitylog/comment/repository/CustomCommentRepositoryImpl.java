package com.juju.cozyformombackend3.domain.communitylog.comment.repository;

import static com.juju.cozyformombackend3.domain.communitylog.comment.model.QComment.*;
import static com.juju.cozyformombackend3.domain.user.model.QUser.*;
import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.comment.dto.CommentDto;
import com.juju.cozyformombackend3.domain.communitylog.comment.dto.QCommentDto;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommentDto> findAllByCozyLogId(Long cozyLogId) {
        return jpaQueryFactory.select(new QCommentDto(comment.id, comment.parentCommentId, comment.content,
                getDateFromQueryLocalDateTime(comment.createdAt),
                getDateFromQueryLocalDateTime(comment.modifiedAt),
                comment.isDeleted,
                user.id, user.nickname, user.profileImageUrl))
            .from(comment)
            .leftJoin(comment.user, user)
            .where(comment.cozyLog.id.eq(cozyLogId))
            .fetch();
    }

    @Override
    public void updateCommentsIsDeletedByUserId(boolean isDelete, Long userId) {
        jpaQueryFactory.update(comment)
            .where(comment.user.id.eq(userId))
            .set(comment.isDeleted, isDelete)
            .set(comment.user, (Expression<? extends User>)null)
            .execute();
    }
}
