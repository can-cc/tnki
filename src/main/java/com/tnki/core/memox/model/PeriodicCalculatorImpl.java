package com.tnki.core.memox.model;

import java.util.Date;

public class PeriodicCalculatorImpl implements PeriodicCalculator {

    private int calcNextLearnDayNumber(int learnTime, float EF) {
        if (learnTime == 0) {
            return 1;
        }
        if (learnTime == 1) {
            return 6;
        }
        return (int) Math.ceil(calcNextLearnDayNumber(learnTime - 1, EF) * EF);
    }


    @Override
    public Date calcNextLearnDate(MemoLearningItem item) {
        if (item.getLearnTime() == 0) {
            return MemoDateUtil.nextDays(1);
        }
        if (item.getLearnTime() == 1) {
            return MemoDateUtil.nextDays(6);
        }
        return calcNextLearnDate();

    }
}
