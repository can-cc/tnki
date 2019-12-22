package com.tnki.core.memox.model;

import com.tnki.core.memox.exception.MemoQualityOutRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;

// memoQuality:
// 5 - perfect response
// 4 - correct response after a hesitation
// 3 - correct response recalled with serious difficulty
// 2 - incorrect response; where the correct one seemed easy to recall
// 1 - incorrect response; the correct one remembered
// 0 - complete blackout.

@Component
public class PeriodicCalculatorImpl implements PeriodicCalculator {
    private static final Logger logger = LoggerFactory.getLogger(PeriodicCalculatorImpl.class);

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
        if (memoQuality < 0 || memoQuality > 5) {
            throw new MemoQualityOutRangeException(memoQuality);
        }
        double ef = (lastEF + (0.1 - (5 - memoQuality)) * ((0.08 + (5 - memoQuality) * 0.02)));
        return limitEF(ef);
    }

    private double limitEF(double ef) {
        if (ef < 1.3) {
            logger.warn("calc ef less than 1.3; ef = " + ef);
            ef = 1.3;
        }
        return ef;
    }
}
