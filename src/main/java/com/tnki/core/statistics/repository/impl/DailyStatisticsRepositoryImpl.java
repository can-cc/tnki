package com.tnki.core.statistics.repository.impl;

import com.tnki.core.share.model.BaseRepository;
import com.tnki.core.statistics.model.DailyStatistics;
import com.tnki.core.statistics.repository.DailyStatisticsRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

@Repository
public class DailyStatisticsRepositoryImpl extends BaseRepository implements DailyStatisticsRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;

    public DailyStatisticsRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<DailyStatistics> findOne(int userID, Date date) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("userID", userID);
        parameter.addValue("date", date);
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    "SELECT total_should_learn, learned FROM daily_learn_statistics WHERE user_id = :userID AND DATEDIFF(date, :date) = 0",
                    parameter,
                    new DailyStatisticsMapper(userID, date)
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void add(DailyStatistics dailyStatistics) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(dailyStatistics);
        jdbcTemplate.update("INSERT INTO daily_learn_statistics(user_id, date, total_should_learn, learned) VALUES (:userID, :date, :totalShouldLearn, :learned)", parameters);
    }

    @Override
    public void update(DailyStatistics dailyStatistics) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(dailyStatistics);
        jdbcTemplate.update(
                "UPDATE daily_learn_statistics SET total_should_learn = :totalShouldLearn, learned = :learned " +
                        "WHERE user_id = :userID AND DATEDIFF(date, :date) = 0",
                parameters
        );
    }

    private static final class DailyStatisticsMapper implements RowMapper<DailyStatistics> {

        private final int userID;
        private final Date date;

        private DailyStatisticsMapper(int userID, Date date) {
            this.userID = userID;
            this.date = date;
        }

        public DailyStatistics mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            DailyStatistics dailyStatistics = new DailyStatistics();
            dailyStatistics.setTotalShouldLearn(resultSet.getInt("total_should_learn"));
            dailyStatistics.setLearned(resultSet.getInt("learned"));
            dailyStatistics.setUserID(this.userID);
            dailyStatistics.setDate(this.date);
            return dailyStatistics;
        }
    }
}
