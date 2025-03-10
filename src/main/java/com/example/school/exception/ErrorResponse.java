package com.example.school.exception;

import jakarta.ws.rs.core.Response.Status;

public class ErrorResponse {
    private String code;
    private String message;
    private int status;
    private String error;
    private String path;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int statusCode) {
        this.status = statusCode;
    }

    public int getStatus() {
        return status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}