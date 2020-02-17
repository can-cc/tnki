package com.tnki.core.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "user already exist")
public class UserAlreadyExistException extends RuntimeException {
}
