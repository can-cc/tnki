package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.UserDailyCheckInRecord;

import java.util.Date;

public interface UserDailyCheckInRecordRepository {
    UserDailyCheckInRecord find(int userID, Date date);
    void add(int userID, Date date);
    void update(UserDailyCheckInRecord userDailyCheckInRecord);
}
