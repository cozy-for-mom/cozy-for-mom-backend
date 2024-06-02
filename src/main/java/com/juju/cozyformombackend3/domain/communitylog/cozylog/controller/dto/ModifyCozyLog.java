package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.ImageInfoDto;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.global.validation.annotation.IsCozyLogMode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ModifyCozyLog {

    @Getter
    public class Request {
        @Min(1)
        private Long id;
        @NotBlank(message = "제목을 입력하세요.")
        private String title;
        private String content;
        private List<ImageInfoDto> imageList;
        @IsCozyLogMode
        private String mode;

        public CozyLogMode getMode() {
            return CozyLogMode.valueOf(mode);
        }

        public List<Long> getImageIdList() {
            return imageList.stream()
                .map(image -> image.getImageId())
                .toList();
        }

        public List<CozyLogImage> getImageList() {
            return imageList.stream()
                .map(image -> CozyLogImage.of(null, image.getImageUrl(), image.getDescription()))
                .toList();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long modifiedCozyLogId;

        public static Response of(Long modifiedCozyLogId) {
            return new Response(modifiedCozyLogId);
        }
    }
}
