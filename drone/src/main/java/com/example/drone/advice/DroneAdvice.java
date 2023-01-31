package com.example.drone.advice;

import am.ik.yavi.core.ConstraintViolationsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

@RestControllerAdvice
public class DroneAdvice {


    @ExceptionHandler(value = {ConstraintViolationsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleConstraintViolationsException(ConstraintViolationsException ex, WebRequest request) {
        return ResponseEntity.ok(Collections.singletonMap("violations", ex.violations().details()));
    }
}
