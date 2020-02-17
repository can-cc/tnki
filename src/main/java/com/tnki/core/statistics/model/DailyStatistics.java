package com.tnki.core.statistics.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class DailyStatistics {
    private int userID;
    private int totalShouldLearn;
    private int learned;
    private Date date;

    public static DailyStatistics create(Date date) {
        DailyStatistics dailyStatistics = new DailyStatistics();
        dailyStatistics.setTotalShouldLearn(0);
        dailyStatistics.setLearned(0);
        dailyStatistics.setDate(date);
        return dailyStatistics;
    }

    public void increaseTotalShouldLearn(int number) {
        this.setTotalShouldLearn(this.getTotalShouldLearn() + number);
    }

    public void increaseLearned(int number) {
        this.setLearned(this.getLearned() + number);
    }
}
