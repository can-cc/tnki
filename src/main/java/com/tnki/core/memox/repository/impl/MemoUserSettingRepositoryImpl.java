package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.model.UserLearnSetting;
import com.tnki.core.memox.repository.MemoUserSettingRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoUserSettingRepositoryImpl implements MemoUserSettingRepository {

    final
    private NamedParameterJdbcTemplate jdbcTemplate;

    public MemoUserSettingRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<UserLearnSetting> findOne(long userID) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    "SELECT user_id, daily_learn_number FROM user_learn_setting WHERE user_id = :userID",
                    new MapSqlParameterSource("userID", userID),
                    new UserLearnSettingMapper()
            ));
        } catch (EmptyResultDataAccessException error) {
            return Optional.empty();
        }
    }

    @Override
    public void save(UserLearnSetting userLearnSetting) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userLearnSetting.getUserId());
        paramMap.put("daily_learn_number", userLearnSetting.getDailyLearnNumber());
        SqlParameterSource param = new MapSqlParameterSource(paramMap);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO user_learn_setting(user_id, daily_learn_number) VALUES (:userId, :daily_learn_number)", param, keyHolder);
    }

    private static final class UserLearnSettingMapper implements RowMapper<UserLearnSetting> {
        public UserLearnSetting mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserLearnSetting userLearnSetting = new UserLearnSetting();
            userLearnSetting.setDailyLearnNumber(resultSet.getInt("daily_learn_number"));
            return userLearnSetting;
        }
    }
}
