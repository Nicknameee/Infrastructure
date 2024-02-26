package io.management.ua.utility;

import io.management.ua.annotations.Export;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Date;

public class TimeUtil {
    @Export
    public static ZonedDateTime getCurrentDateTime() {
        return ZonedDateTime.now(Clock.systemUTC());
    }

    @Export
    public static Date getCurrentDate() {
        return Date.from(getCurrentDateTime().toInstant());
    }
}
