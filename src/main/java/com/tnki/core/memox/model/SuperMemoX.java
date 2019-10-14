package com.tnki.core.memox.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuperMemoX implements Memo {
    @Autowired
    PeriodicCalculator periodicCalculator;

    @Override
    public List<MemoItem> getLearningItem(String userID, int limit, Date date) {
        return null;
    }

    @Override
    public MemoLearningItem learnItem(MemoItem item, int memoQuality) {
        MemoLearningItem learningItem = new MemoLearningItem(item);
        Date nextLearningDate = periodicCalculator.calcNextLearnDate(learningItem, memoQuality);
        return null;
    }

    @Override
    public MemoLearningItem updateLearnItem(MemoLearningItem item) {
        return null;
    }

    @Override
    public void finishLearnItem(MemoLearningItem item) {

    }

}
