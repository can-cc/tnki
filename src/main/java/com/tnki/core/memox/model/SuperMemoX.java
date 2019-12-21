package com.tnki.core.memox.model;

import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class SuperMemoX implements Memo {
    final private PeriodicCalculator periodicCalculator;
    final private MemoItemRepository memoItemRepository;
    final private MemoUserSettingRepository memoUserSettingRepository;

    public SuperMemoX(
            PeriodicCalculator periodicCalculator,
            MemoItemRepository memoItemRepository,
            MemoUserSettingRepository memoUserSettingRepository
    ) {
        this.periodicCalculator = periodicCalculator;
        this.memoItemRepository = memoItemRepository;
        this.memoUserSettingRepository = memoUserSettingRepository;
    }

    @Override
    public List<MemoLearningItem> getLearningItem(int userID) {
        return memoItemRepository.listUserDailyLearnItem(userID, MemoDateUtil.today());
    }

    @Override
    public MemoLearningItem learnItem(MemoItem item, int userID, int memoQuality) {
        MemoLearningItem learningItem = new MemoLearningItem(item, userID);
        Date nextLearningDate = periodicCalculator.calcNextLearnDate(learningItem);
        double nextEF = periodicCalculator.calcNextEF(learningItem.getEF(), memoQuality);

        learningItem.setEF(nextEF);
        learningItem.setLearnTime(learningItem.getLearnTime() + 1);
        learningItem.setNextLearnDate((nextLearningDate));
        this.memoItemRepository.updateLearningItem(learningItem);
        return learningItem;
    }

    @Transactional
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
    public void finishLearnItem(MemoLearningItem item) {}

    @Transactional
    @Override
    public void fillItemToLearn(int userID) {
        int learningCount = memoItemRepository.countUserLearningItem(userID);
        UserLearnSetting userLearnSetting = memoUserSettingRepository.findUserLearnSetting(userID);
        int learnNewNumber = userLearnSetting.getDailyLearnNumber() - learningCount;

        if (learnNewNumber <= 0) {
            return;
        }

        List<MemoItem> memoItems = memoItemRepository.listUserNotLearnItems(userID, learnNewNumber);
        memoItems.parallelStream().forEach(memoItem -> startLearnItem(memoItem, userID));
    }

}
