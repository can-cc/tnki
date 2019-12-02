package com.tnki.core.memox.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PeriodicCalculatorImpl implements PeriodicCalculator {

    private int calcNextLearnDaysNumber(int learnTime, double EF) {
        if (learnTime == 0) {
            return 1;
        }
        if (learnTime == 1) {
            return 6;
        }
        return (int) Math.ceil(calcNextLearnDaysNumber(learnTime - 1, EF) * EF);
    }

    @Override
    public Date calcNextLearnDate(MemoLearningItem item) {
        int nextDays = this.calcNextLearnDaysNumber(item.getLearnTime(), item.getEF());
        return MemoDateUtil.nextDays(nextDays);
    }

    @Override
    public Date getStartLearnDate() {
        return MemoDateUtil.today();
    }

    @Override
    public double calcNextEF(double lastEF, int memoQuality) {
        return lastEF + (0.1 - (5 - memoQuality)) * ((0.08 + (5 - memoQuality) * 0.02));
    }


}
