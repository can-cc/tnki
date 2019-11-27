package com.tnki.core.auth.exception;

import com.tnki.core.share.exception.AppException;

import static com.google.common.collect.ImmutableMap.of;
import static com.tnki.core.auth.AuthErrorCode.UserAlreadyExist;

public class UserAlreadyExistException extends AppException {
    public UserAlreadyExistException(String username) {
        super(UserAlreadyExist, of("username", username));
    }
}
