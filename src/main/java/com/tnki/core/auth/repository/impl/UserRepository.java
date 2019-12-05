package com.tnki.core.auth.repository.impl;

import com.tnki.core.auth.model.User;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserRepository extends BaseRepository implements com.tnki.core.auth.repository.UserRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUsername(String username) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("username", username);
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id, username, password_hash FROM user WHERE username = :username",
                    namedParameters,
                    new BeanPropertyRowMapper<User>(User.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void insertUser(User user) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO user(username, password_hash) VALUES (:username, :passwordHash)", parameters, keyHolder);
        user.setID(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public int queryUserIdByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM user WHERE username = :username",
                new MapSqlParameterSource("username", username),
                Integer.class
        );
    }
}
