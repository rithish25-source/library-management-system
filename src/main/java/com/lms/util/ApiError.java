package com.lms.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

    private String message;

    private HttpStatus status;

    private LocalDateTime timeStamp;

    private List<String> details;
}