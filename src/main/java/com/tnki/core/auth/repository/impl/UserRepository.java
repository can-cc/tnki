package com.tnki.core.auth.repository.impl;

import com.tnki.core.auth.model.User;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository implements com.tnki.core.auth.repository.UserRepository {
    final
    private NamedParameterJdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate, @Qualifier("PasswordEncoder") PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("username", username);
        try {
            return (User) jdbcTemplate.queryForObject(
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
        user.setID(keyHolder.getKey().intValue());
    }
}
