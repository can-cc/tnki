package com.tnki.core.memox;

import com.tnki.core.share.exception.ErrorCode;

public enum MemoxErrorCode implements ErrorCode {
    DailyCheckInError(500, " Check in fail "),
    MemoQualityOutRangeError(403, " memo quality out of range ");

    private int status;
    private String message;

    MemoxErrorCode(int status, String message) {
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
