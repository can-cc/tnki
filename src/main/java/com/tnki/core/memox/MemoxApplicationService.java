package com.tnki.core.memox;

import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoItemFactory;
import com.tnki.core.memox.model.UserLearnSetting;
import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.memox.repository.MemoRepository;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MemoxApplicationService {

    final
    private MemoItemFactory memoItemFactory;

    final
    private MemoItemRepository memoItemRepository;

    final
    private MemoRepository memoRepository;

    final
    private MemoUserSettingRepository memoUserSettingRepository;

    @Autowired
    public MemoxApplicationService(MemoItemFactory memoItemFactory, MemoItemRepository memoItemRepository, MemoRepository memoRepository, MemoUserSettingRepository memoUserSettingRepository) {
        this.memoItemFactory = memoItemFactory;
        this.memoItemRepository = memoItemRepository;
        this.memoRepository = memoRepository;
        this.memoUserSettingRepository = memoUserSettingRepository;
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
        List<MemoItem> memoItems = memoItemRepository.listUserUnStartedItems(username, learnNewNumber);
        // memoItems.parallelStream().map(MemoItem::)
    }

    public void initUserLearnSetting(int userId) {
        memoRepository.insertUserLearnSetting(userId);
    }

}
