package com.juju.cozyformombackend3.domain.communitylog.comment.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.comment.dto.CommentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FindCommentList {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final List<CommentDto> comments;

        public static Response of(List<CommentDto> comments) {
            return new Response(comments);
        }
    }
}

