package com.tnki.core.memox;

import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.command.LearnItemCommand;
import com.tnki.core.memox.model.*;
import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.memox.repository.MemoRepository;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import com.tnki.core.memox.repository.UserDailyCheckInRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemoxApplicationService {
    final private MemoItemFactory memoItemFactory;
    final private MemoItemRepository memoItemRepository;
    final private MemoRepository memoRepository;
    final private MemoUserSettingRepository memoUserSettingRepository;
    final private PeriodicCalculator periodicCalculator;
    final private UserDailyCheckInRecordRepository userDailyCheckInRecordRepository;
    final private Memo memo;

    @Autowired
    public MemoxApplicationService(
            MemoItemFactory memoItemFactory,
            MemoItemRepository memoItemRepository,
            MemoRepository memoRepository,
            MemoUserSettingRepository memoUserSettingRepository,
            PeriodicCalculator periodicCalculator,
            UserDailyCheckInRecordRepository userDailyCheckInRecordRepository,
            Memo memo
    ) {
        this.memoItemFactory = memoItemFactory;
        this.memoItemRepository = memoItemRepository;
        this.memoRepository = memoRepository;
        this.memoUserSettingRepository = memoUserSettingRepository;
        this.periodicCalculator = periodicCalculator;
        this.userDailyCheckInRecordRepository = userDailyCheckInRecordRepository;
        this.memo = memo;
    }

    int createLearnItem(CreateLearnItemCommand command, int userID) {
      MemoItem item = memoItemFactory.createMemoItemFromCommand(command);
        memoItemRepository.insertItem(item, userID);
        log.info("Created learn item [{}].", item.getID());
        return item.getID();
    }

    void userDailyCheckIn(int userID) {
        UserDailyCheckInRecord userDailyCheckInRecord = userDailyCheckInRecordRepository.findUserDailyCheckInRecord(userID, MemoDateUtil.today());
        if (userDailyCheckInRecord == null) {
            memo.fillItemToLearn(userID);
            userDailyCheckInRecordRepository.insertUserDailyCheckInRecord(userID, MemoDateUtil.today());
        }
    }

    void learnItem(LearnItemCommand learnItemCommand, int userID) {
        MemoLearningItem memoLearningItem = memoItemRepository.findMemoLearningItem(learnItemCommand.getItemID(), userID);
        memo.learnItem(memoLearningItem, userID, learnItemCommand.getMemoQuality());
    }

     List<MemoLearningItem> getDailyLearnItem(int userID) {
       return memo.getLearningItem(userID);
    }
}
