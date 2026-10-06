package com.lms.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lms.util.ApiError;

@ControllerAdvice
public class LibraryExceptionHandler {


    // =====================================================
    // BOOK NOT FOUND
    // =====================================================

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object>
            handleBookNotFoundException(
                    BookNotFoundException exception) {

        ApiError error =
                new ApiError();

        error.setMessage(
                exception.getMessage());

        error.setStatus(
                HttpStatus.NOT_FOUND);

        error.setTimeStamp(
                LocalDateTime.now());

        List<String> details =
                new ArrayList<>();

        details.add("Book Not Found");

        error.setDetails(details);


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    // =====================================================
    // USER NOT FOUND
    // =====================================================

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object>
            handleUserNotFoundException(
                    UserNotFoundException exception) {

        ApiError error =
                new ApiError();

        error.setMessage(
                exception.getMessage());

        error.setStatus(
                HttpStatus.NOT_FOUND);

        error.setTimeStamp(
                LocalDateTime.now());

        List<String> details =
                new ArrayList<>();

        details.add("User Not Found");

        error.setDetails(details);


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    // =====================================================
    // LIBRARY NOT FOUND
    // =====================================================

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<Object>
            handleLibraryNotFoundException(
                    LibraryNotFoundException exception) {

        ApiError error =
                new ApiError();

        error.setMessage(
                exception.getMessage());

        error.setStatus(
                HttpStatus.NOT_FOUND);

        error.setTimeStamp(
                LocalDateTime.now());

        List<String> details =
                new ArrayList<>();

        details.add("Library Not Found");

        error.setDetails(details);


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    // =====================================================
    // ADDRESS NOT FOUND
    // =====================================================

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Object>
            handleAddressNotFoundException(
                    AddressNotFoundException exception) {

        ApiError error =
                new ApiError();

        error.setMessage(
                exception.getMessage());

        error.setStatus(
                HttpStatus.NOT_FOUND);

        error.setTimeStamp(
                LocalDateTime.now());

        List<String> details =
                new ArrayList<>();

        details.add("Address Not Found");

        error.setDetails(details);


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    
    @ExceptionHandler(AddressAlreadyAssignedException.class)
    public ResponseEntity<Object> handleAddressAlreadyAssigned(
            AddressAlreadyAssignedException exception) {

        Map<String, Object> error = new java.util.HashMap<>();

        error.put("status", HttpStatus.CONFLICT.value());
        error.put("error", "Address Conflict");
        error.put("message", exception.getMessage());
        error.put("timestamp", LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(
            RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}