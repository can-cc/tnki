package com.tnki.core.memox.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLearnSetting {
    private long userId;
    private int dailyLearnNumber;

    final static int DefaultDailyLearnNumber = 10;

    static public UserLearnSetting initUserLearnSetting(long userID) {
        UserLearnSetting userLearnSetting = new UserLearnSetting();
        userLearnSetting.setUserId(userID);
        userLearnSetting.setDailyLearnNumber(DefaultDailyLearnNumber);
        return userLearnSetting;
    }
}
