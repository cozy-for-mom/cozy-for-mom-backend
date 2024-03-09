package com.juju.cozyformombackend3.global.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(Long userId, MultipartFile image);

    void remove(String username, String imageUrl);
}
