package com.tnki.core.memox.model;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicCalculatorImplTest {

    private final PeriodicCalculator periodicCalculator = new PeriodicCalculatorImpl();

    @org.junit.jupiter.api.Test
    void calcNextLearnDate() {
        MemoLearningItem item = new MemoLearningItem();
        item.setLearnTime(0);
        Date nextLearnDate = periodicCalculator.calcNextLearnDate(item);

        assertEquals(nextLearnDate.toString(), MemoDateUtil.nextDays(1).toString());
    }

    @org.junit.jupiter.api.Test
    void calcNextEF() {
        double nextEF = periodicCalculator.calcNextEF(1.3, 5);
        assertEquals(nextEF, 1.308);
    }
}