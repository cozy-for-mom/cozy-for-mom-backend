package com.juju.cozyformombackend3.domain.user.controller.dto;

import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDate;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresentDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UpdateMyInfo {
    @Getter
    public static class Request {
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickname;

        private String introduce;

        private String image;

        @IsLocalDate
        @IsPastOrPresentDate
        private String birth;

        private String email;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long userId;

        public static Response of(final Long userId) {
            return new Response(userId);
        }
    }

}
