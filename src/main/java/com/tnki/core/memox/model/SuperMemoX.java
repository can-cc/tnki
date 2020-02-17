package com.tnki.core.memox.model;

import com.tnki.core.common.MemoDateUtil;
import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.memox.repository.MemoLearnItemRepository;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import com.tnki.core.statistics.StatisticsApplicationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class SuperMemoX {
    public static final double LEARN_ITEM_INITIAL_EF = 1.3;
    final private PeriodicCalculator periodicCalculator;
    final private MemoItemRepository memoItemRepository;
    final private MemoUserSettingRepository memoUserSettingRepository;
    final private MemoLearnItemRepository memoLearnItemRepository;
    final private StatisticsApplicationService statisticsApplicationService;

    public SuperMemoX(
            PeriodicCalculator periodicCalculator,
            MemoItemRepository memoItemRepository,
            MemoUserSettingRepository memoUserSettingRepository,
            MemoLearnItemRepository memoLearnItemRepository,
            StatisticsApplicationService statisticsApplicationService
    ) {
        this.periodicCalculator = periodicCalculator;
        this.memoItemRepository = memoItemRepository;
        this.memoLearnItemRepository = memoLearnItemRepository;
        this.memoUserSettingRepository = memoUserSettingRepository;
        this.statisticsApplicationService= statisticsApplicationService;
    }

    public List<MemoLearningItem> getLearningItem(int userID) {
        return memoLearnItemRepository.findAll(userID, MemoDateUtil.today());
    }

    public MemoLearningItem learnItem(MemoLearningItem memoLearningItem, int memoQuality) {
        Date nextLearningDate = periodicCalculator.calcNextLearnDate(memoLearningItem);
        double nextEF = periodicCalculator.calcNextEF(memoLearningItem.getEF(), memoQuality);
        memoLearningItem.setEF(nextEF);
        memoLearningItem.setLearnTime(memoLearningItem.getLearnTime() + 1);
        memoLearningItem.setLastLearnDate(MemoDateUtil.today());
        memoLearningItem.setNextLearnDate(nextLearningDate);
        this.memoLearnItemRepository.update(memoLearningItem);
        return memoLearningItem;
    }

    @Transactional
    public void startLearnItem(MemoItem memoItem, int userID) {
        MemoLearningItem memoLearningItem = new MemoLearningItem(memoItem, userID);
        memoLearningItem.setLearnTime(0);
        memoLearningItem.setEF(LEARN_ITEM_INITIAL_EF);
        memoLearningItem.setNextLearnDate(periodicCalculator.getStartLearnDate());
        memoLearnItemRepository.add(memoLearningItem);
    }

    public void finishLearnItem(MemoLearningItem item) {
    }

    @Transactional
    public void fillItemToLearn(int userID) {
        int learningCount = memoLearnItemRepository.shouldLearnSize(userID, MemoDateUtil.today());
        UserLearnSetting userLearnSetting = memoUserSettingRepository.findUserLearnSetting(userID);
        int learnNewNumber = userLearnSetting.getDailyLearnNumber() - learningCount;

        if (learnNewNumber <= 0) {
            return;
        }

        List<MemoItem> memoItems = memoItemRepository.findAllUnLearned(userID, learnNewNumber);
        memoItems.parallelStream().forEach(memoItem -> startLearnItem(memoItem, userID));
        this.statisticsApplicationService.increaseDailyTotalLearn(userID, learnNewNumber);
    }

}
