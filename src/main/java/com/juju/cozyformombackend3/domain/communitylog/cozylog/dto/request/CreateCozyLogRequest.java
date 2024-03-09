package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.ImageInfoDto;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.validation.annotation.IsCozyLogMode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema
public class CreateCozyLogRequest {

    @Schema(defaultValue = "제목")
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @Schema(defaultValue = "내용")
    private String content;

    private List<ImageInfoDto> imageList;

    @Schema(defaultValue = "PUBLIC")
    @IsCozyLogMode
    private String mode;

    public CozyLog toEntity(User user) {
        return CozyLog.of(user, this.title, this.content, getCozyLogImageList(), CozyLogMode.valueOf(this.mode));
    }

    private List<CozyLogImage> getCozyLogImageList() {
        return imageList.stream()
            .map(image -> CozyLogImage.of(null, image.getImageUrl(), image.getDescription()))
            .toList();
    }
}
