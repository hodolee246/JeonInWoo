package com.rest.api;

public class BoardRunTimeException extends RuntimeException {

    private String message;
    private int errorCode;

    public BoardRunTimeException(String message, int errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
