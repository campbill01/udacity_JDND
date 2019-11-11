package com.example.demo.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    
    ApiError() {
        timestamp = LocalDateTime.now();
    }
 
    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }
 
    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }
 
    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }


    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return this.debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<ApiSubError> getSubErrors() {
        return this.subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }


 }