package com.tnki.core.memox;

import com.tnki.core.auth.AuthApplicationService;
import com.tnki.core.memox.command.CreateLearnItemCommand;
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
    final private AuthApplicationService authApplicationService;
    final private PeriodicCalculator periodicCalculator;

    @Autowired
    public MemoxApplicationService(MemoItemFactory memoItemFactory, MemoItemRepository memoItemRepository, MemoRepository memoRepository, MemoUserSettingRepository memoUserSettingRepository, AuthApplicationService authApplicationService, PeriodicCalculator periodicCalculator) {
        this.memoItemFactory = memoItemFactory;
        this.memoItemRepository = memoItemRepository;
        this.memoRepository = memoRepository;
        this.memoUserSettingRepository = memoUserSettingRepository;
        this.authApplicationService = authApplicationService;
        this.periodicCalculator = periodicCalculator;
    }

    public int createLearnItem(CreateLearnItemCommand command) {
        MemoItem item = memoItemFactory.create(command);
        memoItemRepository.insertItem(item);
        log.info("Created learn item [{}].", item.getID());
        return item.getID();
    }

    public void userDailyCheckIn(String username) {
        int learningCount = memoItemRepository.countUserLearningItem(username);
        UserLearnSetting userLearnSetting = memoUserSettingRepository.findUserLearnSetting(username);
        int learnNewNumber = userLearnSetting.getDailyLearnNumber() - learningCount;

        if (learningCount <= 0) {
            return;
        }

        Memo memo = new SuperMemoX(periodicCalculator, memoRepository, memoItemRepository);
        int userID = authApplicationService.getUserIdByUsername(username);

        List<MemoItem> memoItems = memoItemRepository.listUserUnStartedItems(username, learnNewNumber);
        memoItems.parallelStream().forEach(memoItem -> memo.startLearnItem(memoItem, userID));
    }

    public void initUserLearnSetting(int userId) {
        memoRepository.insertUserLearnSetting(userId);
    }

}
