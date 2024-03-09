package com.juju.cozyformombackend3.global.image.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadImageRequest {

    // @ApiModelProperty(value = "이미지", example = "https://cozyformom.s3.ap-northeast-2.amazonaws.com/1.jpg", required = true)
    private MultipartFile image;
}