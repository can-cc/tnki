package com.tnki.core.memox.exception;

import com.tnki.core.share.exception.AppException;

import static com.google.common.collect.ImmutableMap.of;
import static com.tnki.core.memox.MemoxErrorCode.MemoQualityOutRangeError;

public class MemoQualityOutRangeException extends AppException {
    public MemoQualityOutRangeException(int value) {
        super(MemoQualityOutRangeError, of("value", value));
    }
}
