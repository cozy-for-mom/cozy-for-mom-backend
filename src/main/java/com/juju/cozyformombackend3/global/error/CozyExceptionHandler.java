package com.juju.cozyformombackend3.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.juju.cozyformombackend3.global.dto.response.ErrorResponse;
import com.juju.cozyformombackend3.global.error.code.ErrorCode;
import com.juju.cozyformombackend3.global.error.exception.AuthException;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CozyExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException e, HttpServletRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn(errorCode.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(ErrorResponse.of(request, e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e,
        HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        String firstErrorMessage = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(e.getStatusCode())
            .body(ErrorResponse.of(request, e.getStatusCode(), firstErrorMessage));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthException e, HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(ErrorResponse.of(request, e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception e, HttpServletRequest request) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(request, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
