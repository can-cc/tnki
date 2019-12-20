package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.UserDailyCheckInRecord;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Date;

public interface UserDailyCheckInRecordRepository {
    void insertUserDailyCheckInRecord(int userID, Date date);
    UserDailyCheckInRecord findUserDailyCheckInRecord(int userID, Date date);
    void increaseUserDailyCheckInRecord(int userID, Date date, UserDailyCheckInRecord userDailyCheckInRecord);
}
