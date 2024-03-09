package com.juju.cozyformombackend3.global.image.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadImageRequest {

    // @ApiModelProperty(value = "이미지", example = "https://cozyformom.s3.ap-northeast-2.amazonaws.com/1.jpg", required = true)
    @NotNull(message = "등록할 이미지 파일이 존재하지 않습니다.")
    private MultipartFile image;
}