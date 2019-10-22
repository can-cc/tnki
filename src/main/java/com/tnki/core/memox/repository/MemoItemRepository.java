package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.share.model.BaseRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoItemRepository extends BaseRepository {
    public List<MemoItem> getNotStartedItems(String userID, int litmit) {
        List<MemoItem> items = new ArrayList<>();
        return items;
    }

    public void saveItems(MemoItem item) {

    }


}
