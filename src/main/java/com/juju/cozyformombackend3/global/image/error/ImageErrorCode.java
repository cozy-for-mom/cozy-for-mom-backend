package com.juju.cozyformombackend3.global.image.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {
    BAD_REQUEST_FILE_TYPE(400, "파일 형식이 잘못되었습니다."),
    CONFLICT_FAIL_TO_UPLOAD_IMAGE(409, "파일 업로드에 실패했습니다.");

    private final int status;
    private final String message;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}