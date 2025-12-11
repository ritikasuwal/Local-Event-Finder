package com.rabin.backend.exception;

import com.rabin.backend.dto.response.GenericApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.jackson.databind.exc.UnrecognizedPropertyException;


@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<GenericApiResponse<Object>> buildResponse(String message, HttpStatus status) {
        GenericApiResponse<Object> response = new GenericApiResponse<>(false, message, null, status.value());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericApiResponse<Object>> handleRuntimeException(RuntimeException e){
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericApiResponse<Object>> handleException(Exception e){
        return buildResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<GenericApiResponse<Object>> handleUnrecognizedProperty(UnrecognizedPropertyException e){
        String field = e.getPropertyName();
        return buildResponse("Unknown field: " + field, HttpStatus.BAD_REQUEST);
    }
}
