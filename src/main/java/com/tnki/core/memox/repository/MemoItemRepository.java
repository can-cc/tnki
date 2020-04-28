package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;

import java.util.List;

public interface MemoItemRepository {
    List<MemoItem> findAllUnLearned(long userID, int limit);
    void add(MemoItem item, long userID);
    MemoItem findOne(int memoItemID);
}
