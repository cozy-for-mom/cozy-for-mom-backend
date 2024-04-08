package com.juju.cozyformombackend3.domain.user.dto;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Gender;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.util.DateParser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class SignUpDto {
    @Getter
    @AllArgsConstructor
    public static class Request {
        private final UserInfo userInfo;
        private final BabyInfo babyInfo;

        @Getter
        @AllArgsConstructor
        public static class UserInfo {
            private final String oauthId;
            private final String oauthType;
            private final String name;
            private final String nickname;
            private final String birth;
            private final String email;

            public OAuth2Registration getOAuthType() {
                return OAuth2Registration.ofType(oauthType);
            }

            public LocalDate getBirth() {
                return DateParser.stringDateToLocalDate(birth);
            }
        }

        @Getter
        @AllArgsConstructor
        public static class BabyInfo {
            private final String dueAt;
            private final String lastPeriodAt;
            private final List<BabyDto> babies;

            @Getter
            @AllArgsConstructor
            public static class BabyDto {
                private final String name;
                private final String gender;

                public Gender getGender() {
                    return Gender.ofType(gender);
                }
            }
        }
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
