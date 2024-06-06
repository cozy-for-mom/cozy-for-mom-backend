package com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ModifyComment {
    @Getter
    public static class Request {
        private Long commentId;
        private Long parentId;
        private String comment;
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
