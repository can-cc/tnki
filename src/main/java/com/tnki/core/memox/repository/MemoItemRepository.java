package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoLearningItem;

import java.util.Date;
import java.util.List;

public interface MemoItemRepository {
    List<MemoItem> findAllUnLearned(int userID, int limit);
    void add(MemoItem item, int userID);
    MemoItem findOne(int memoItemID);
}
