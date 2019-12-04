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
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class MemoItemRepositoryImpl extends BaseRepository implements MemoItemRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;

    public MemoItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MemoItem findMemoItem(int memoItemID) {
        return jdbcTemplate.queryForObject(
                "SELECT id, front, back, tip from learn_item WHERE id = :id",
                new MapSqlParameterSource("id", memoItemID),
                new MemoItemMapper()
        );
    }

    @Override
    public List<MemoItem> listUserUnStartedItems(int userID, int limit) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("userID", userID);
        parameter.addValue("limit", limit);
        return jdbcTemplate.query(
                "SELECT a.id, a.front, a.back, a.tip from learn_item as a \n" +
                        "LEFT JOIN user_create_item_relation as c ON a.id = c.item_id \n" +
                        "LEFT JOIN user_learn_item as b ON a.id = b.user_id \n" +
                        "WHERE b.user_id IS NULL AND c.user_id = :userID LIMIT :limit",
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

    @Transactional
    @Override
    public void insertItem(MemoItem memoItem, int userID) {
        SqlParameterSource memoItemParams = new BeanPropertySqlParameterSource(memoItem);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO learn_item(front, tip, back) VALUES (:front, :tip, :back)", memoItemParams, keyHolder);
        memoItem.setID(Objects.requireNonNull(keyHolder.getKey()).intValue());

        Map<String, Integer> memoRelationParams = new HashMap<>();
        memoRelationParams.put("userID", userID);
        memoRelationParams.put("itemID", memoItem.getID());
        jdbcTemplate.update("INSERT INTO user_create_item_relation (user_id, item_id) VALUES (:userID, :itemID)", memoRelationParams);
    }

    @Override
    public void insertLearningItem(MemoLearningItem memoLearningItem) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", memoLearningItem.getUserID());
        paramMap.put("memoItemID", memoLearningItem.getMemoItem().getID());
        paramMap.put("ef", memoLearningItem.getEF());
        paramMap.put("learnTime", memoLearningItem.getLearnTime());
        jdbcTemplate.update("INSERT INTO user_learn_item(user_id, item_id, ef, n, is_learning) VALUES (:userID, :memoItemID, :ef, :learnTime, true)", paramMap);
    }

    @Override
    public List<MemoLearningItem> listUserDailyLearnItem(int userID, Date today) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("userID", userID);

        MemoLearningItemMapper memoLearningItemMapper =  new MemoLearningItemMapper(userID);
        return jdbcTemplate.query(
                "SELECT a.user_id, a.ef, a.n a.next_learn_date, b.id, b.front, b.back, b.tip from user_learn_item as a \n" +
                        "LEFT JOIN learn_item as b ON a.item_id = b.id \n" +
                        "WHERE a.user_id = :userId",
                parameter,
                memoLearningItemMapper
        );
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

    private static final class MemoLearningItemMapper implements RowMapper<MemoLearningItem> {
        private int userID;

        private MemoLearningItemMapper(int userID) {
            this.userID = userID;
        }

        public MemoLearningItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            MemoItem memoItem = new MemoItem();
            memoItem.setID(resultSet.getInt("id"));
            memoItem.setFront(resultSet.getString("front"));
            memoItem.setBack(resultSet.getString("back"));
            memoItem.setTip(resultSet.getString("tip"));

            MemoItemFactory memoItemFactory = new MemoItemFactory();

            return memoItemFactory.createMemoLearningItem(memoItem, userID);
        }
    }
}
