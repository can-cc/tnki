package com.tnki.core.memox.model;

import java.util.Calendar;
import java.util.Date;

public class MemoDateUtil {

    static Date nextDays(int days) {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, days);

        return c.getTime();
    }
}
