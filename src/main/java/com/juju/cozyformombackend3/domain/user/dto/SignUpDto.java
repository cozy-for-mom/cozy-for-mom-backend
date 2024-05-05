package com.juju.cozyformombackend3.domain.user.dto;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Baby;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.model.Gender;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class SignUpDto {
    @Getter
    @AllArgsConstructor
    public static class Request {
        private final UserInfo userInfo;
        private final BabyInfo babyInfo;

        public BabyProfile toBabyProfile() {
            BabyProfile babyProfile = BabyProfile.of(babyInfo.babies.size(), getBabyInfo().getLastPeriodAt(),
                getBabyInfo().getDueAt(), null);
            babyInfo.getBabies()
                .forEach(babyDto -> babyProfile.addBaby(Baby.of(babyProfile, babyDto.name, babyDto.getGender())));

            return babyProfile;
        }

        @Getter
        @AllArgsConstructor
        public static class UserInfo {
            private final String oauthType;
            private final String name;
            private final String nickname;
            private final String birth;
            private final String email;
            private final String deviceToken;

            public OAuth2Registration getOAuthType() {
                return OAuth2Registration.ofType(oauthType);
            }

            public LocalDate getBirth() {
                return stringDateToLocalDate(birth);
            }
        }

        @Getter
        @AllArgsConstructor
        public static class BabyInfo {
            private final String dueAt;
            private final String lastPeriodAt;
            private final List<BabyDto> babies;

            public LocalDate getDueAt() {
                return stringDateToLocalDate(dueAt);
            }

            public LocalDate getLastPeriodAt() {
                return stringDateToLocalDate(lastPeriodAt);
            }

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

    // TODO: 임시 dto 계층 분리해낼 것
    @Getter
    @RequiredArgsConstructor
    public static class SignUpInfo {
        private final Response response;
        private final String token;

        public static SignUpInfo of(final Response response, final String token) {
            return new SignUpInfo(response, token);
        }
    }
}
