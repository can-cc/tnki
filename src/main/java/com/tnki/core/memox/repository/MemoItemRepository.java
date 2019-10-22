package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoItemRepository extends BaseRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<MemoItem> getNotStartedItems(String userID, int litmit) {
        List<MemoItem> items = new ArrayList<>();
        return items;
    }

    public void saveItem(MemoItem item) {
        int ID = jdbcTemplate.update("INSERT INTO learn_item(front, tip, back) VALUES (?, ?, ?)", item.getFront(), item.getTip(), item.getBack());
        item.setID(ID);
    }
}
