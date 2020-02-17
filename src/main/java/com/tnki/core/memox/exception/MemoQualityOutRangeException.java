package com.tnki.core.memox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "memo quality out range")
public class MemoQualityOutRangeException extends RuntimeException {
}
