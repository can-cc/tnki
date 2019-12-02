package com.tnki.core.memox.model;

import org.springframework.stereotype.Component;

import java.util.Date;

public interface PeriodicCalculator {
    Date calcNextLearnDate(MemoLearningItem item);
    Date getStartLearnDate();
    double calcNextEF(double lastEF, int memoQuality);

}
