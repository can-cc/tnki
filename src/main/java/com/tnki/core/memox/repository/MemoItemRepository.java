package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoLearningItem;

import java.util.List;

public interface MemoItemRepository {
    List<MemoItem> listUserUnStartedItems(int userID, int limit);
    int countUserLearningItem(int userID);
    void insertItem(MemoItem item);
    void insertLearningItem(MemoLearningItem memoLearningItem);
}
