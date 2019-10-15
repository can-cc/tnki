package com.tnki.core.memox.model;

import java.util.Date;

public interface PeriodicCalculator {
    Date calcNextLearnDate(MemoLearningItem item);
    double calcNextEF(double lastEF, int memoQuality);
}
