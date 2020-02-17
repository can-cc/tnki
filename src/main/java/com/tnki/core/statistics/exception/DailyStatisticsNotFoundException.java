package com.tnki.core.statistics.exception;

import com.tnki.core.share.exception.AppException;
import static com.tnki.core.statistics.StatisticsErrorCode.DailyStatisticsNotFoundError;

public class DailyStatisticsNotFoundException extends AppException {
    public DailyStatisticsNotFoundException() {
        super(DailyStatisticsNotFoundError, null);
    }
}
