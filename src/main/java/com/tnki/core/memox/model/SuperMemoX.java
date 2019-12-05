package com.tnki.core.memox.model;

import com.tnki.core.memox.repository.MemoItemRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class SuperMemoX implements Memo {
    final private PeriodicCalculator periodicCalculator;
    final private MemoItemRepository memoItemRepository;

    public SuperMemoX(
            PeriodicCalculator periodicCalculator,
            MemoItemRepository memoItemRepository
    ) {
        this.periodicCalculator = periodicCalculator;
        this.memoItemRepository = memoItemRepository;
    }

    @Override
    public List<MemoItem> getLearningItem(String userID, int limit, Date date) {
        return null;
    }

    @Override
    public MemoLearningItem learnItem(MemoItem item, int userID, int memoQuality) {
        MemoLearningItem learningItem = new MemoLearningItem(item, userID);
        Date nextLearningDate = periodicCalculator.calcNextLearnDate(learningItem);
        double nextEF = periodicCalculator.calcNextEF(learningItem.getEF(), memoQuality);

        learningItem.setEF(nextEF);
        learningItem.setLearnTime(learningItem.getLearnTime() + 1);
        learningItem.setNextLearnDate((nextLearningDate));
        return learningItem;
    }

    @Override
    public void startLearnItem(MemoItem memoItem, int userID) {
        double LEARN_ITEM_INITIAL_EF = 1.3;
        MemoLearningItem memoLearningItem = new MemoLearningItem(memoItem, userID);
        memoLearningItem.setLearnTime(0);
        memoLearningItem.setEF(LEARN_ITEM_INITIAL_EF);
        memoLearningItem.setNextLearnDate(periodicCalculator.getStartLearnDate());
        memoItemRepository.insertLearningItem(memoLearningItem);
    }

    @Override
    public void finishLearnItem(MemoLearningItem item) {

    }

}
