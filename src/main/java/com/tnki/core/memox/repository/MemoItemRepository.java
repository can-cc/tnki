package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoLearningItem;

import java.util.List;

public interface MemoItemRepository {
    MemoItem findMemoItem(int memoItem);
    List<MemoItem> listUserUnStartedItems(int userID, int limit);
    int countUserLearningItem(int userID);
    void insertItem(MemoItem item, int userID);
    void insertLearningItem(MemoLearningItem memoLearningItem);
}
