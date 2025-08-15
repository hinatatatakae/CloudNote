package com.hinata.cloudnote.config;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(err -> err.getField() + " → " + err.getDefaultMessage())
				.collect(Collectors.joining("; "));
		return ResponseEntity.badRequest()
				.body(Map.of("error", errors));
	}
}
