package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoItemFactory;
import com.tnki.core.memox.model.MemoLearningItem;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class MemoItemRepositoryImpl extends BaseRepository implements MemoItemRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;

    public MemoItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Nullable
    public MemoItem findOne(int memoItemID) {
        return jdbcTemplate.queryForObject(
                "SELECT id, front, back, tip, created_at, updated_at from learn_item WHERE id = :id",
                new MapSqlParameterSource("id", memoItemID),
                new MemoItemMapper()
        );
    }

    @Override
    public List<MemoItem> findAllUnLearned(int userID, int limit) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("userID", userID);
        parameter.addValue("limit", limit);
        return jdbcTemplate.query(
                "SELECT item.* from learn_item as item " +
                        "LEFT JOIN user_create_item_relation as relation ON item.id = relation.item_id " +
                        "LEFT JOIN user_learn_item as learn ON relation.item_id = learn.item_id " +
                        "WHERE relation.user_id = :userID AND learn.user_id IS NULL LIMIT :limit",
                parameter,
                new MemoItemMapper()
        );
    }

    @Transactional
    @Override
    public void add(MemoItem memoItem, int userID) {
        SqlParameterSource memoItemParams = new BeanPropertySqlParameterSource(memoItem);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO learn_item(front, tip, back) VALUES (:front, :tip, :back)", memoItemParams, keyHolder);
        memoItem.setID(Objects.requireNonNull(keyHolder.getKey()).intValue());

        Map<String, Integer> memoRelationParams = new HashMap<>();
        memoRelationParams.put("userID", userID);
        memoRelationParams.put("itemID", memoItem.getID());
        jdbcTemplate.update("INSERT INTO user_create_item_relation (user_id, item_id) VALUES (:userID, :itemID)", memoRelationParams);
    }

    private static final class MemoItemMapper implements RowMapper<MemoItem> {
        public MemoItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            MemoItem memoItem = new MemoItem();
            memoItem.setID(resultSet.getInt("id"));
            memoItem.setFront(resultSet.getString("front"));
            memoItem.setFront(resultSet.getString("back"));
            memoItem.setTip(resultSet.getString("tip"));
            memoItem.setCreatedAt(resultSet.getDate("created_at"));
            memoItem.setUpdatedAt(resultSet.getDate("updated_at"));
            return memoItem;
        }
    }


}
