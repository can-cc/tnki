package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoLearningItem;

import java.util.Date;
import java.util.List;

public interface MemoItemRepository {
    MemoItem findMemoItem(int memoItem);
    List<MemoItem> listUserNotLearnItems(int userID, int limit);
    int countUserLearningItem(int userID);
    void insertItem(MemoItem item, int userID);
    void insertLearningItem(MemoLearningItem memoLearningItem);
    void updateLearningItem(MemoLearningItem memoLearningItem);
    List<MemoLearningItem> listUserDailyLearnItem(int userID, Date today);
}
