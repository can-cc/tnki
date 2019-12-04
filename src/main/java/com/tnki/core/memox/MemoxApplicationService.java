package com.tnki.core.memox;

import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.command.LearnItemCommand;
import com.tnki.core.memox.model.*;
import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.memox.repository.MemoRepository;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
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
    final private Memo memo;

    @Autowired
    public MemoxApplicationService(
            MemoItemFactory memoItemFactory,
            MemoItemRepository memoItemRepository,
            MemoRepository memoRepository,
            MemoUserSettingRepository memoUserSettingRepository,
            PeriodicCalculator periodicCalculator,
            Memo memo
    ) {
        this.memoItemFactory = memoItemFactory;
        this.memoItemRepository = memoItemRepository;
        this.memoRepository = memoRepository;
        this.memoUserSettingRepository = memoUserSettingRepository;
        this.periodicCalculator = periodicCalculator;
        this.memo = memo;
    }

    int createLearnItem(CreateLearnItemCommand command, int userID) {
      MemoItem item = memoItemFactory.createMemoItemFromCommand(command);
        memoItemRepository.insertItem(item, userID);
        log.info("Created learn item [{}].", item.getID());
        return item.getID();
    }

    void userDailyCheckIn(int userID) {
        int learningCount = memoItemRepository.countUserLearningItem(userID);
        UserLearnSetting userLearnSetting = memoUserSettingRepository.findUserLearnSetting(userID);
        int learnNewNumber = userLearnSetting.getDailyLearnNumber() - learningCount;

        if (learnNewNumber <= 0) {
            return;
        }

        List<MemoItem> memoItems = memoItemRepository.listUserNotLearnItems(userID, learnNewNumber);
        memoItems.parallelStream().forEach(memoItem -> memo.startLearnItem(memoItem, userID));
    }

    void learnItem(LearnItemCommand learnItemCommand, int userID) {
        MemoItem memoItem = memoItemRepository.findMemoItem(learnItemCommand.getItemID());
        memo.learnItem(memoItem, userID, learnItemCommand.getMemoQuality());
    }

     List<MemoLearningItem> getDailyLearnItem(int userID) {
        return memoItemRepository.listUserDailyLearnItem(userID, MemoDateUtil.today());
    }
}
