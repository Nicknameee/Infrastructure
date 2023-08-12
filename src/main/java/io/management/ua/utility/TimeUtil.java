package io.management.ua.utility;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Date;

public class TimeUtil {
    public static ZonedDateTime getCurrentDateTime() {
        return ZonedDateTime.now(Clock.systemUTC());
    }

    public static Date getCurrentDate() {
        return Date.from(getCurrentDateTime().toInstant());
    }
}
