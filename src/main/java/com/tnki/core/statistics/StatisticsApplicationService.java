package com.tnki.core.statistics;

import com.tnki.core.statistics.model.DailyStatistics;
import org.springframework.stereotype.Service;

@Service
public class StatisticsApplicationService {

    DailyStatistics getUserDailyStatistics(int userID)  {
        return new DailyStatistics(0, 0);
    }
}
