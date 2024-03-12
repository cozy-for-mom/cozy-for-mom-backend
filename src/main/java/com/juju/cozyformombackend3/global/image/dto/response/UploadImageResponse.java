package com.juju.cozyformombackend3.global.image.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadImageResponse {
    private final String imageUrl;

    public static UploadImageResponse of(final String imageUrl) {
        return new UploadImageResponse(imageUrl);
    }
}
