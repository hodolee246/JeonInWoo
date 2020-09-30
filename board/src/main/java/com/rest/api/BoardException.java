package com.rest.api;

public class BoardException extends Exception {

    private String message;
    private int errorCode;

    public BoardException(String message, int errorCode) {
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
