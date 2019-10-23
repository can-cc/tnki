package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoItemRepository extends BaseRepository {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<MemoItem> getNotStartedItems(String userID, int litmit) {
        List<MemoItem> items = new ArrayList<>();
        return items;
    }

    public void saveItem(MemoItem item) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(item);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO learn_item(front, tip, back) VALUES (:front, :tip, :back)", parameters, keyHolder);
        item.setID(keyHolder.getKey().intValue());
    }
}
