package com.juju.cozyformombackend3.global.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;
import com.juju.cozyformombackend3.global.util.DateParser;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String timestamp;
    private final int status;
    private final String message;
    private final String path;

    @Builder
    public ErrorResponse(LocalDateTime timestamp, int status, String message, String path) {
        this.timestamp = DateParser.dateTimeToStringFormatDateTime(timestamp);
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public static ErrorResponse of(HttpServletRequest request, ErrorCode errorCode) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .path(request.getRequestURI())
            .build();
    }

    public static ErrorResponse of(HttpServletRequest request, HttpStatus httpStatus) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(httpStatus.value())
            .message(httpStatus.getReasonPhrase())
            .path(request.getRequestURI())
            .build();
    }

    public static ErrorResponse of(HttpServletRequest request, HttpStatusCode statusCode, String message) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(statusCode.value())
            .message(message)
            .path(request.getRequestURI())
            .build();
    }
}
