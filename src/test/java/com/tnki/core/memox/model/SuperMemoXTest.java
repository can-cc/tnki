package com.tnki.core.memox.model;

import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Date;

import static com.tnki.core.memox.model.SuperMemoX.LEARN_ITEM_INITIAL_EF;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class SuperMemoXTest {
    private Memo memo;
    private MemoItemRepository memoItemRepository;
    private MemoUserSettingRepository memoUserSettingRepository;

    @BeforeEach
    void initEach() {
        PeriodicCalculator periodicCalculator = new PeriodicCalculatorImpl();
        memoItemRepository = mock(MemoItemRepository.class);
        memoUserSettingRepository = mock(MemoUserSettingRepository.class);
        memo = new SuperMemoX(periodicCalculator, memoItemRepository, memoUserSettingRepository);
    }

    @Test
    void learnItem() {
        MemoItem memoItem = new MemoItem();
        memoItem.setID(10001);
        MemoLearningItem memoLearningItem = new MemoLearningItem(memoItem, 1002);
        memoLearningItem.setNextLearnDate(new Date(157702000 * 1000));
        memoLearningItem.setEF(1.3);

        Date now = new Date();

        memo.learnItem(memoLearningItem, 3);
        assertEquals(1.3, memoLearningItem.getEF());
        assertEquals(1, (memoLearningItem.getNextLearnDate().getTime() - now.getTime()) / ( 1000 * 60 * 60 * 24));

        memo.learnItem(memoLearningItem, 5);
        assertEquals(1.308, memoLearningItem.getEF());
        assertEquals(6, (memoLearningItem.getNextLearnDate().getTime() - now.getTime()) / ( 1000 * 60 * 60 * 24));

        memo.learnItem(memoLearningItem, 5);
        memo.learnItem(memoLearningItem, 5);
        assertEquals(1.324, memoLearningItem.getEF());
        assertEquals(11, (memoLearningItem.getNextLearnDate().getTime() - now.getTime()) / ( 1000 * 60 * 60 * 24));

        verify(memoItemRepository, times(4)).updateLearningItem(memoLearningItem);
    }

    @Test
    void startLearnItem() {
        int userID = 1002;
        MemoItem memoItem = new MemoItem();
        memoItem.setID(10001);
        memo.startLearnItem(memoItem, userID);

        ArgumentCaptor<MemoLearningItem> captor = ArgumentCaptor.forClass(MemoLearningItem.class);
        verify(memoItemRepository, times(1)).insertLearningItem(captor.capture());
        assertEquals(LEARN_ITEM_INITIAL_EF, captor.getValue().getEF());
        assertEquals(0, captor.getValue().getLearnTime());
        assertEquals(1002, captor.getValue().getUserID());
        assertTrue(captor.getValue().isLearning());
    }

}