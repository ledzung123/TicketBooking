package com.example.TicketBooking.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ProblemDetail> handleAppExceptions(AppException ex) {
        HttpStatus status = ex.getStatus();
        String errorCode = ex.getErrorCode();

        // Tu dong sinh type field, status, detail, title
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());

        problemDetail.setProperty("errorCode", errorCode);

        log.warn("{}: {}", ex.getErrorCode(), ex.getMessage());

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String defaultMessage = error.getDefaultMessage();
            errors.put(fieldName, defaultMessage);
        });

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request contents.");

        problemDetail.setProperty("errors", errors);

        log.warn("Validation failed: {}", errors);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintValidation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        for(ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            // Lay ten tham so (vi du nhu keyword trong search movie)
            String fieldName = violation.getPropertyPath().toString();
            // Lay message
            String message = violation.getMessage();

            // Keep only the field name when the property path includes a method name.
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }

            errors.put(fieldName, message);
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid variable on URL");

        problemDetail.setProperty("errors", errors);

        log.warn("Variable URL validation failed: {}", errors);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Parse JSON error: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST
                , "Wrong JSON format or wrong variable type");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Violate database constraint: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT
                , "Data duplicate or violate database constraint");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Authentication failed: invalid credentials");

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED
                , ex.getMessage());

        problemDetail.setProperty("errorCode", "BAD_CREDENTIALS");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        log.warn("Authorization denied: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "You can not access these material"
        );
        problemDetail.setProperty("errorCode", "FORBIDDEN");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUncaughtException(Exception ex) {
        log.error("Uncaught exception", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR
                , "The system is under maintenance, please try again later");


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
