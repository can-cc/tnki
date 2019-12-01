package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemoItemRepositoryImpl extends BaseRepository implements MemoItemRepository {

    final
    private NamedParameterJdbcTemplate jdbcTemplate;

    public MemoItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MemoItem> listUserUnStartedItems(String username, int litmit) {
        return jdbcTemplate.query(
                "SELECT a.id, a.front, a.back, a.tip from learn_item as a LEFT JOIN user_learning_item as b ON a.id = b.user_id WHERE b.user_id = NULL AND a.username =: username LIMIT :limit",
                new MapSqlParameterSource("username", username),
                new MemoItemMapper()
        );
    }

    @Override
    public int countUserLearningItem(String username) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("username", username);
        return jdbcTemplate.queryForObject("SELECT count(item_id) as count from user_learn_item WHERE username = :username", paramMap, Integer.class);
    }

    @Override
    public void insertItem(MemoItem item) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(item);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO learn_item(front, tip, back) VALUES (:front, :tip, :back)", parameters, keyHolder);
        item.setID(keyHolder.getKey().intValue());
    }

    private static final class MemoItemMapper implements RowMapper<MemoItem> {
        public MemoItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            MemoItem memoItem = new MemoItem();
            memoItem.setID(resultSet.getInt("id"));
            memoItem.setFront(resultSet.getString("front"));
            memoItem.setFront(resultSet.getString("back"));
            memoItem.setTip(resultSet.getString("tip"));
            return memoItem;
        }
    }
}
