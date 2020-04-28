package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.UserDailyCheckInRecord;

import java.util.Date;

public interface UserDailyCheckInRecordRepository {
    UserDailyCheckInRecord find(long userID, Date date);
    void add(long userID, Date date);
    void update(UserDailyCheckInRecord userDailyCheckInRecord);
}
