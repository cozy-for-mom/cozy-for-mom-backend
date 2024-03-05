package com.juju.cozyformombackend3.global.error;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.juju.cozyformombackend3.global.dto.response.ErrorResponse;
import com.juju.cozyformombackend3.global.error.code.ErrorCode;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CozyExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		log.info(errorCode.getMessage());
		return ResponseEntity.status(e.getErrorCode().getStatus())
			.body(ErrorResponse.of(LocalDateTime.now(), errorCode.getStatus(), errorCode.getMessage()));
	}
}
