package com.tnki.core.memox.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDailyCheckInRecord {
    private long userID;
    private Date date;
    private int time;

    public void increaseTime() {
        this.time++;
    }
}
