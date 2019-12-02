package com.tnki.core.memox.exception;

import com.tnki.core.share.exception.AppException;

import static com.google.common.collect.ImmutableMap.of;
import static com.tnki.core.auth.AuthErrorCode.UserAlreadyExist;
import static com.tnki.core.memox.MemoxErrorCode.DailyCheckInError;

public class DailyCheckInException extends AppException {
    public DailyCheckInException(Throwable cause, String username) {
        super(cause, DailyCheckInError, of("username", username));
    }

    public DailyCheckInException(String username) {
        super(DailyCheckInError, of("username", username));
    }
}


