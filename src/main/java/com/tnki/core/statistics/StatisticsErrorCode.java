package com.tnki.core.statistics;

import com.tnki.core.share.exception.ErrorCode;

public enum StatisticsErrorCode implements ErrorCode {
    DailyStatisticsNotFoundError(404, " daily statistics not found ");

    private int status;
    private String message;

    StatisticsErrorCode(int status, String message) {
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
