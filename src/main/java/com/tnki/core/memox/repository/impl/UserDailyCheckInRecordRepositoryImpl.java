package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.model.UserDailyCheckInRecord;
import com.tnki.core.memox.repository.UserDailyCheckInRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDailyCheckInRecordRepositoryImpl implements UserDailyCheckInRecordRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    UserDailyCheckInRecordRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void insertUserDailyCheckInRecord(int userID, Date date) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", userID);
        paramMap.put("date", date);
        jdbcTemplate.update("INSERT INTO user_daily_check_in_record(user_id, date, time) VALUES (:userID, :date, 1)", paramMap);
    }

    @Override
    public
    @Nullable
    UserDailyCheckInRecord findUserDailyCheckInRecord(int userID, Date date) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userID", userID);
        paramMap.put("date", date);
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT user_id, date, time from user_daily_check_in_record WHERE user_id = :userID AND DATE(date) = DATE(:date)",
                    paramMap,
                    new UserDailyCheckInRecordRepositoryImpl.UserDailyCheckInRecordMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Transactional
    @Override
    public void increaseUserDailyCheckInRecord(int userID, Date date, UserDailyCheckInRecord userDailyCheckInRecord) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("time", userDailyCheckInRecord.getTime());
        paramMap.put("userID", userID);
        paramMap.put("date", date);
        jdbcTemplate.update("UPDATE user_daily_check_in_record SET time = :time " +
                "WHERE user_id = :userID and DATE(date) = DATE(:date)", paramMap);
    }

    private static final class UserDailyCheckInRecordMapper implements RowMapper<UserDailyCheckInRecord> {
        public UserDailyCheckInRecord mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserDailyCheckInRecord userDailyCheckInRecord = new UserDailyCheckInRecord();
            userDailyCheckInRecord.setUserID(resultSet.getInt("user_id"));
            userDailyCheckInRecord.setDate(resultSet.getDate("date"));
            userDailyCheckInRecord.setTime(resultSet.getInt("time"));
            return userDailyCheckInRecord;
        }
    }
}
