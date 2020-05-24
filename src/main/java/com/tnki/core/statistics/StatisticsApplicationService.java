package com.tnki.core.statistics;

import com.tnki.core.common.MemoDateUtil;
import com.tnki.core.statistics.model.DailyStatistics;
import com.tnki.core.statistics.repository.DailyStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class StatisticsApplicationService {
    final private DailyStatisticsRepository dailyStatisticsRepository;

    @Autowired
    public StatisticsApplicationService(DailyStatisticsRepository dailyStatisticsRepository) {
        this.dailyStatisticsRepository = dailyStatisticsRepository;
    }

    public void increaseDailyTotalLearn(long userID, int number) {
        Optional<DailyStatistics> dailyStatisticsOptional = dailyStatisticsRepository.findOne(userID, MemoDateUtil.today());
        if (dailyStatisticsOptional.isPresent()) {
            DailyStatistics dailyStatistics = dailyStatisticsOptional.get();
            dailyStatistics.increaseTotalShouldLearn(number);
            this.dailyStatisticsRepository.update(dailyStatistics);
        } else {
            DailyStatistics dailyStatistics = DailyStatistics.create(MemoDateUtil.today());
            dailyStatistics.setUserID(userID);
            dailyStatistics.increaseTotalShouldLearn(number);
            this.dailyStatisticsRepository.add(dailyStatistics);
        }
    }

    public void increaseDailyLearned(long userID, int number) {
        Optional<DailyStatistics> dailyStatisticsOptional = dailyStatisticsRepository.findOne(userID, MemoDateUtil.today());
        DailyStatistics dailyStatistics = dailyStatisticsOptional.orElseGet(() -> DailyStatistics.create(MemoDateUtil.today()));
        dailyStatistics.increaseLearned(number);
        this.dailyStatisticsRepository.update(dailyStatistics);
    }

    public Optional<DailyStatistics> getDailyStatistics(long userID, Date date) {
        return dailyStatisticsRepository.findOne(userID, date);
    }
}
