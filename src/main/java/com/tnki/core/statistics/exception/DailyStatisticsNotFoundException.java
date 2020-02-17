package com.tnki.core.statistics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "daily statistics not found")
public class DailyStatisticsNotFoundException extends RuntimeException {

}
