package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;

import java.util.List;

public interface MemoItemRepository {
    List<MemoItem> listUserUnStartedItems(String username, int litmit);

    int countUserLearningItem(String username);

    void insertItem(MemoItem item);
}
