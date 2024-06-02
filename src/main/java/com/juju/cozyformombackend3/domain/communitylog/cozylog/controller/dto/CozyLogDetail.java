package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.ImageInfoDto;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CozyLogDetail {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private final Writer writer;
        private final Long id;
        private final String title;
        private final String content;
        private List<ImageInfoDto> imageList;
        private final String mode;
        private final String createdAt;
        private String updatedAt;
        private final Long scrapCount;
        private final Long viewCount;
        private final boolean isScraped;

        public static Response of(CozyLog foundCozyLog, Long scrapCount, boolean isScraped) {
            return Response.builder()
                .id(foundCozyLog.getId())
                .writer(new Writer(foundCozyLog.getUser()))
                .title(foundCozyLog.getTitle())
                .content(foundCozyLog.getContent())
                .imageList(getImageDtoList(foundCozyLog.getCozyLogImageList()))
                .mode(String.valueOf(foundCozyLog.getMode()))
                .createdAt(localDateTimeToStringFormatDateTime(foundCozyLog.getCreatedAt()))
                .updatedAt(localDateTimeToStringFormatDateTime(foundCozyLog.getModifiedAt()))
                .scrapCount(scrapCount)
                .viewCount(foundCozyLog.getView())
                .isScraped(isScraped)
                .build();
        }

        private static List<ImageInfoDto> getImageDtoList(List<CozyLogImage> imageList) {
            return imageList.stream()
                .map(image -> ImageInfoDto.of(image))
                .toList();
        }

        @Getter
        @AllArgsConstructor
        private static class Writer {
            private Long id;
            private String nickname;
            private String profileImageUrl;

            public Writer(User user) {
                this.id = user.getId();
                this.nickname = user.getNickname();
                this.profileImageUrl = user.getProfileImageUrl();
            }
        }

        public boolean getIsScraped() {
            return isScraped;
        }
    }
}
