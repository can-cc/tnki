package com.tnki.core.memox.model;

import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class SuperMemoX implements Memo {
    public static final double LEARN_ITEM_INITIAL_EF = 1.3;
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
    public MemoLearningItem learnItem(MemoLearningItem memoLearningItem, int memoQuality) {
        Date nextLearningDate = periodicCalculator.calcNextLearnDate(memoLearningItem);
        double nextEF = periodicCalculator.calcNextEF(memoLearningItem.getEF(), memoQuality);

        memoLearningItem.setEF(nextEF);
        memoLearningItem.setLearnTime(memoLearningItem.getLearnTime() + 1);
        memoLearningItem.setNextLearnDate(nextLearningDate);
        this.memoItemRepository.updateLearningItem(memoLearningItem);
        return memoLearningItem;
    }

    @Transactional
    @Override
    public void startLearnItem(MemoItem memoItem, int userID) {
        MemoLearningItem memoLearningItem = new MemoLearningItem(memoItem, userID);
        memoLearningItem.setLearnTime(0);
        memoLearningItem.setEF(LEARN_ITEM_INITIAL_EF);
        memoLearningItem.setNextLearnDate(periodicCalculator.getStartLearnDate());
        memoItemRepository.insertLearningItem(memoLearningItem);
    }

    @Override
    public void finishLearnItem(MemoLearningItem item) {
    }

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
