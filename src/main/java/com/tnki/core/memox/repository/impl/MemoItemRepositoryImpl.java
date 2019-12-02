package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoLearningItem;
import com.tnki.core.memox.repository.MemoItemRepository;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class MemoItemRepositoryImpl extends BaseRepository implements MemoItemRepository {

    final
    private NamedParameterJdbcTemplate jdbcTemplate;

    public MemoItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MemoItem> listUserUnStartedItems(int userID, int limit) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("userID", userID);
        parameter.addValue("limit", limit);
        return jdbcTemplate.query(
                "SELECT a.id, a.front, a.back, a.tip from learn_item as a LEFT JOIN user_learn_item as b ON a.id = b.user_id WHERE b.user_id IS NULL AND b.user_id = :userID LIMIT :limit",
                parameter,
                new MemoItemMapper()
        );
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int countUserLearningItem(int userID) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("userID", userID);
        return jdbcTemplate.<Integer>queryForObject("SELECT count(item_id) as count from user_learn_item WHERE user_id = :userID", paramMap, Integer.class);
    }

    @Override
    public void insertItem(MemoItem memoItem) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(memoItem);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO learn_item(front, tip, back) VALUES (:front, :tip, :back)", parameters, keyHolder);
        memoItem.setID(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public void insertLearningItem(MemoLearningItem memoLearningItem) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", memoLearningItem.getUserID());
        paramMap.put("memoItemID", memoLearningItem.getMemoItem().getID());
        paramMap.put("ef", memoLearningItem.getEF());
        paramMap.put("n", memoLearningItem.getLearnTime());
        jdbcTemplate.update("INSERT INTO user_learn_item(user_id, item_id, ef, n, is_learning) VALUES (:userID, :memoItemID, :ef, :learnTime, true)", paramMap);
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
