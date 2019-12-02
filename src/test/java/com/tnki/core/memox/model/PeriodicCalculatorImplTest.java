package com.tnki.core.memox.model;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicCalculatorImplTest {

    private final PeriodicCalculator periodicCalculator = new PeriodicCalculatorImpl();

    @org.junit.jupiter.api.Test
    void calcNextLearnDate() {
        MemoItem memoItem = new MemoItem();
        MemoLearningItem memoLearningItem = new MemoLearningItem(memoItem, 101);
        memoLearningItem.setLearnTime(0);
        Date nextLearnDate = periodicCalculator.calcNextLearnDate(memoLearningItem);

        assertEquals(nextLearnDate.toString(), MemoDateUtil.nextDays(1).toString());
    }

    @org.junit.jupiter.api.Test
    void calcNextEF() {
        double nextEF = periodicCalculator.calcNextEF(1.3, 5);
        assertEquals(nextEF, 1.308);
    }
}