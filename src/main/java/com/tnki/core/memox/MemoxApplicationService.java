package com.tnki.core.memox;

import com.tnki.core.common.MemoDateUtil;
import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.command.LearnItemCommand;
import com.tnki.core.memox.model.*;
import com.tnki.core.memox.repository.*;
import com.tnki.core.statistics.StatisticsApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemoxApplicationService {
    final private MemoItemFactory memoItemFactory;
    final private MemoItemRepository memoItemRepository;
    final private UserDailyCheckInRecordRepository userDailyCheckInRecordRepository;
    final private MemoLearnItemRepository memoLearnItemRepository;
    final private SuperMemoX superMemoX;
    final private StatisticsApplicationService statisticsApplicationService;

    @Autowired
    public MemoxApplicationService(
            MemoItemFactory memoItemFactory,
            MemoItemRepository memoItemRepository,
            UserDailyCheckInRecordRepository userDailyCheckInRecordRepository,
            MemoLearnItemRepository memoLearnItemRepository,
            SuperMemoX superMemoX,
            StatisticsApplicationService statisticsApplicationService
    ) {
        this.memoItemFactory = memoItemFactory;
        this.memoItemRepository = memoItemRepository;
        this.userDailyCheckInRecordRepository = userDailyCheckInRecordRepository;
        this.memoLearnItemRepository = memoLearnItemRepository;
        this.superMemoX = superMemoX;
        this.statisticsApplicationService = statisticsApplicationService;
    }

    int createLearnItem(CreateLearnItemCommand command, int userID) {
        MemoItem item = memoItemFactory.createMemoItemFromCommand(command);
        memoItemRepository.add(item, userID);
        log.info("Created learn item [{}].", item.getID());
        return item.getID();
    }

    void checkIn(int userID) {
        UserDailyCheckInRecord userDailyCheckInRecord = userDailyCheckInRecordRepository.find(userID, MemoDateUtil.today());
        superMemoX.fillItemToLearn(userID);
        if (userDailyCheckInRecord == null) {
            userDailyCheckInRecordRepository.add(userID, MemoDateUtil.today());
        } else {
            userDailyCheckInRecord.increaseTime();
            userDailyCheckInRecordRepository.update(userDailyCheckInRecord);
        }
    }

    void learnItem(LearnItemCommand learnItemCommand, int userID) {
        MemoLearningItem memoLearningItem = memoLearnItemRepository.findOne(learnItemCommand.getItemID(), userID);
        superMemoX.learnItem(memoLearningItem, learnItemCommand.getMemoQuality());
        this.statisticsApplicationService.increaseDailyLearned(userID, 1);
    }

    List<MemoLearningItem> getDailyLearnItem(int userID) {
        return superMemoX.getLearningItem(userID);
    }

}
