package com.juju.cozyformombackend3.global.image;

import org.springframework.web.multipart.MultipartFile;

import com.juju.cozyformombackend3.global.image.dto.response.UploadImageResponse;

public interface ImageService {
    UploadImageResponse upload(Long userId, MultipartFile image);

    void remove(String username, String imageUrl);
}
