package com.tnki.core.auth;

import com.tnki.core.share.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
    UserAlreadyExist(409, "User already exist");

    private int status;
    private String message;

    AuthErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}