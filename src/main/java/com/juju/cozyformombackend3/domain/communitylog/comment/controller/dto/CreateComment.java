package com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto;

import com.juju.cozyformombackend3.domain.communitylog.comment.model.Comment;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CreateComment {
    @Getter
    public static class Request {
        private Long parentId;
        private String comment;

        public Comment toEntity(User writer) {
            return Comment.of(comment, parentId, writer, null);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {

        private final Long commentId;

        public static Response of(Long commentId) {
            return new Response(commentId);
        }
    }

}
