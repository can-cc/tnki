package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.model.UserLearnSetting;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MemoUserSettingRepositoryImpl implements MemoUserSettingRepository {

    final
    private NamedParameterJdbcTemplate jdbcTemplate;

    public MemoUserSettingRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserLearnSetting findUserLearnSetting(int userID) {
        return jdbcTemplate.queryForObject(
                "SELECT user_id, daily_learn_number FROM user_learn_setting WHERE user_id = :userID",
                new MapSqlParameterSource("userID", userID),
                new UserLearnSettingMapper()
        );
    }

    private static final class UserLearnSettingMapper implements RowMapper<UserLearnSetting> {
        public UserLearnSetting mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserLearnSetting userLearnSetting = new UserLearnSetting();
            userLearnSetting.setDailyLearnNumber(resultSet.getInt("daily_learn_number"));
            return userLearnSetting;
        }
    }
}
