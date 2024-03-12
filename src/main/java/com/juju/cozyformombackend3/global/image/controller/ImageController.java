package com.juju.cozyformombackend3.global.image.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import com.juju.cozyformombackend3.global.image.ImageService;
import com.juju.cozyformombackend3.global.image.dto.request.UploadImageRequest;
import com.juju.cozyformombackend3.global.image.dto.response.UploadImageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<SuccessResponse> uploadImage(
        @LoginUserId Long userId,
        @ModelAttribute UploadImageRequest request) {
        UploadImageResponse response = imageService.upload(userId, request.getImage());

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
