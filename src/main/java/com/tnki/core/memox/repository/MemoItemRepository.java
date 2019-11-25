package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;

import java.util.List;

public interface MemoItemRepository {
    List<MemoItem> listUnStartedItems(String userID, int litmit);

    void inertItem(MemoItem item);
}
