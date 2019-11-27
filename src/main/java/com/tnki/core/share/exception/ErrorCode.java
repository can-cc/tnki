package com.tnki.core.share.exception;

public interface ErrorCode {
    int getStatus();

    String getMessage();

    String getCode();
}