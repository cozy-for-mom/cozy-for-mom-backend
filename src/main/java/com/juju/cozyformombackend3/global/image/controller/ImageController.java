package com.juju.cozyformombackend3.global.image.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.image.ImageService;
import com.juju.cozyformombackend3.global.image.dto.request.UploadImageRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public String recordFoodImage(
        @LoginUserId Long userId,
        @ModelAttribute UploadImageRequest request) {
        return imageService.upload(userId, request.getImage());
    }
}
