package com.tnki.core.statistics.repository;

import com.tnki.core.statistics.model.DailyStatistics;

import java.util.Date;
import java.util.Optional;

public interface DailyStatisticsRepository {
    Optional<DailyStatistics> findOne(int userID, Date date);
    void add(DailyStatistics dailyStatistics);
    void update(DailyStatistics dailyStatistics);
}
