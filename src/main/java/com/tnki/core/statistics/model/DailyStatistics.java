package com.tnki.core.statistics.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyStatistics {
    private int learnedNumber;
    private int needLearnNumber;

    public DailyStatistics(int learnedNumber, int needLearnNumber) {
        this.learnedNumber = learnedNumber;
        this.needLearnNumber = needLearnNumber;
    }

}
