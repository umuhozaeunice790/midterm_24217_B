package com.example.BuildingPermitMs.exception;

import com.example.BuildingPermitMs.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, WebRequest request) {
        logger.error("❌ RESOURCE NOT FOUND: {}", ex.getMessage());
        logger.error("📍 Request Path: {}", request.getDescription(false));
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException ex, WebRequest request) {
        logger.error("❌ DUPLICATE RESOURCE: {}", ex.getMessage());
        logger.error("📍 Request Path: {}", request.getDescription(false));
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Conflict",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        logger.error("❌ DATABASE CONSTRAINT VIOLATION");
        logger.error("📍 Request Path: {}", request.getDescription(false));
        logger.error("🔍 Root Cause: {}", ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Unknown");
        logger.error("💡 Full Error: ", ex);
        
        String message = "Database constraint violation. ";
        if (ex.getRootCause() != null) {
            String rootMsg = ex.getRootCause().getMessage();
            if (rootMsg.contains("unique") || rootMsg.contains("duplicate")) {
                message += "Duplicate entry detected. Check unique fields (email, code, license number).";
            } else if (rootMsg.contains("foreign key")) {
                message += "Invalid reference. The referenced record does not exist.";
            } else if (rootMsg.contains("not-null")) {
                message += "Required field is missing.";
            } else {
                message += rootMsg;
            }
        }
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            message,
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, WebRequest request) {
        logger.error("❌ INVALID JSON FORMAT");
        logger.error("📍 Request Path: {}", request.getDescription(false));
        logger.error("🔍 Error: {}", ex.getMessage());
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            "Invalid JSON format or missing required fields. Check your request body.",
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, WebRequest request) {
        logger.error("❌ INVALID ARGUMENT: {}", ex.getMessage());
        logger.error("📍 Request Path: {}", request.getDescription(false));
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        logger.error("❌ UNEXPECTED ERROR OCCURRED");
        logger.error("📍 Request Path: {}", request.getDescription(false));
        logger.error("🔍 Error Type: {}", ex.getClass().getName());
        logger.error("💬 Error Message: {}", ex.getMessage());
        logger.error("📚 Stack Trace: ", ex);
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred: " + ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
