package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoItemFactory;
import com.tnki.core.memox.model.MemoLearningItem;
import com.tnki.core.memox.repository.MemoLearnItemRepository;
import com.tnki.core.memox.repository.mapper.MyBatisMemoLearningItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class MemoLearnItemRepositoryImpl implements MemoLearnItemRepository {

    final private NamedParameterJdbcTemplate jdbcTemplate;
    final private MyBatisMemoLearningItemMapper myBatisMemoLearningItemMapper;

    @Autowired
    public MemoLearnItemRepositoryImpl(
            NamedParameterJdbcTemplate jdbcTemplate,
            MyBatisMemoLearningItemMapper myBatisMemoLearningItemMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.myBatisMemoLearningItemMapper = myBatisMemoLearningItemMapper;
    }

    @Override
    public Optional<Integer> shouldLearnSize(long userID, Date nextLearnDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", userID);
        paramMap.put("nextLearnDate", nextLearnDate);
        return Optional.ofNullable(jdbcTemplate.<Integer>queryForObject("" +
                        "SELECT count(item_id) as count " +
                        "FROM user_learn_item " +
                        "WHERE user_id = :userID " +
                        "AND next_learn_date = :nextLearnDate",
                paramMap, Integer.class));
    }

    @Override
    public Optional<Integer> learnedSize(long userID, Date lastLearnDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", userID);
        paramMap.put("lastLearnDate", lastLearnDate);
        return Optional.ofNullable(jdbcTemplate.<Integer>queryForObject("SELECT count(item_id) as count from user_learn_item WHERE user_id = :userID AND last_learn_date = :lastLearnDate", paramMap, Integer.class));
    }

    @Transactional
    @Override
    public void add(MemoLearningItem memoLearningItem) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", memoLearningItem.getUserID());
        paramMap.put("memoItemID", memoLearningItem.getMemoItem().getID());
        paramMap.put("ef", memoLearningItem.getEF());
        paramMap.put("learnTime", memoLearningItem.getLearnTime());
        paramMap.put("next_learn_date", memoLearningItem.getNextLearnDate());
        jdbcTemplate.update("INSERT INTO user_learn_item(user_id, item_id, ef, n, is_learning, next_learn_date) VALUES (:userID, :memoItemID, :ef, :learnTime, true, :next_learn_date)", paramMap);
    }

    @Override
    public List<MemoLearningItem> findAll(long userID, Date nextLearnDate) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("userID", userID);
        parameter.addValue("nextLearnDate", nextLearnDate);

        MemoLearningItemMapper memoLearningItemMapper = new MemoLearningItemMapper(userID);
        return jdbcTemplate.query(
                "SELECT a.user_id, a.ef, a.n, a.next_learn_date, a.is_learning, a.created_at as item_created_at, a.updated_at as item_updated_at, " +
                        "b.id, b.front, b.back, b.tip, b.created_at as learn_created_at, b.updated_at as learn_updated_at " +
                        "from user_learn_item as a " +
                        "LEFT JOIN learn_item as b ON a.item_id = b.id " +
                        "WHERE a.user_id = :userID AND a.next_learn_date <= :nextLearnDate;",
                parameter,
                memoLearningItemMapper
        );
    }

    @Override
    public List<MemoLearningItem> find(long userID, int offset, int limit) {
        return myBatisMemoLearningItemMapper.find(userID, offset, limit);
    }

    @Override
    public MemoLearningItem findOne(int memoItemID, long userID) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("userID", userID);
        parameter.addValue("memoItemID", memoItemID);
        return jdbcTemplate.queryForObject(
                "SELECT a.user_id, a.ef, a.n, a.next_learn_date, a.is_learning, a.created_at as item_created_at, a.updated_at as item_updated_at, " +
                        "b.id, b.front, b.back, b.tip, b.created_at as learn_created_at, b.updated_at as learn_updated_at " +
                        "from user_learn_item as a " +
                        "LEFT JOIN learn_item as b ON a.item_id = b.id " +
                        "WHERE a.user_id = :userID AND a.item_ID = :memoItemID",
                parameter,
                new MemoLearningItemMapper(userID)
        );
    }

    @Override
    public void update(MemoLearningItem memoLearningItem) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", memoLearningItem.getUserID());
        paramMap.put("memoItemID", memoLearningItem.getMemoItem().getID());
        paramMap.put("ef", memoLearningItem.getEF());
        paramMap.put("nextLearnDate", memoLearningItem.getNextLearnDate());
        paramMap.put("lastLearnDate", memoLearningItem.getLastLearnDate());
        paramMap.put("learnTime", memoLearningItem.getLearnTime());
        paramMap.put("isLearning", memoLearningItem.isLearning());
        jdbcTemplate.update(
                "UPDATE user_learn_item SET n = :learnTime, is_learning = :isLearning, ef = :ef, next_learn_date = :nextLearnDate, last_learn_date = :lastLearnDate " +
                        "WHERE item_id = :memoItemID AND user_id = :userID",
                paramMap
        );
    }

    private static final class MemoLearningItemMapper implements RowMapper<MemoLearningItem> {
        private long userID;

        private MemoLearningItemMapper(long userID) {
            this.userID = userID;
        }

        public MemoLearningItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            MemoItem memoItem = new MemoItem();
            memoItem.setID(resultSet.getInt("id"));
            memoItem.setFront(resultSet.getString("front"));
            memoItem.setBack(resultSet.getString("back"));
            memoItem.setTip(resultSet.getString("tip"));
            memoItem.setCreatedAt(resultSet.getDate("item_created_at"));
            memoItem.setUpdatedAt(resultSet.getDate("item_updated_at"));

            MemoItemFactory memoItemFactory = new MemoItemFactory();

            MemoLearningItem memoLearningItem = memoItemFactory.createMemoLearningItem(memoItem, userID);
            memoLearningItem.setEF(resultSet.getDouble("ef"));
            memoLearningItem.setLearnTime(resultSet.getInt("n"));
            memoLearningItem.setLearning(resultSet.getBoolean("is_learning"));
            memoLearningItem.setNextLearnDate(resultSet.getDate("next_learn_date"));
            memoItem.setCreatedAt(resultSet.getDate("learn_created_at"));
            memoItem.setUpdatedAt(resultSet.getDate("learn_updated_at"));
            return memoLearningItem;
        }
    }
}
