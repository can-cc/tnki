package com.tnki.core.memox.repository.impl;

import com.tnki.core.memox.repository.MemoRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoRepositoryImpl implements MemoRepository {

    final private NamedParameterJdbcTemplate jdbcTemplate;

    public MemoRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertUserLearnSetting(int userId) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        SqlParameterSource param = new MapSqlParameterSource(paramMap);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO user_learn_setting(user_id) VALUES (:userId)", param, keyHolder);
    }
}
