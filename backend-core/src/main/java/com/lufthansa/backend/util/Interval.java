package com.lufthansa.backend.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

import static java.util.Objects.requireNonNull;

@Component
public class Interval {

    public static boolean overlaps(Date begin1, Date end1, Date begin2, Date end2) {

        java.sql.Time sqlTimeEnd2 = new Time(end2.getTime());
        java.sql.Time sqlTimeOpen2 = new Time(begin2.getTime());

        LocalTime openTime1Local = LocalDateTime.ofInstant(begin1.toInstant(),
                ZoneId.systemDefault()).toLocalTime();

        LocalTime endTime1Local = LocalDateTime.ofInstant(end1.toInstant(),
                ZoneId.systemDefault()).toLocalTime();

        LocalTime endTime2Local = sqlTimeEnd2.toLocalTime();

        LocalTime openTime2Local = sqlTimeOpen2.toLocalTime();

        if(begin1.equals(begin2) && end1.equals(end2)){
            return true;
        }


        if (openTime1Local.equals(endTime1Local)) { // zero length, cannot overlap anything
            return false;
        }
        if (openTime2Local.equals(end2)) {
            return false;
        }



        // If any interval is 12 hours or longer,
        // the other one is necessarily included, that is, overlaps
        if (is12HoursOrLonger(begin1, end1)) {
            return true;
        }
        if (is12HoursOrLonger(begin2, end2)) {
            return true;
        }

        // Convert all times to AM
        openTime1Local = toAm(openTime1Local);
        endTime1Local = toAm(endTime1Local);
        openTime2Local = toAm(openTime2Local);
        endTime2Local = toAm(endTime2Local);

        // For the two intervals *not* to overlap we must be able to go forward
        // from begin1 through end1 and openTime2Local to end2 in this order either
        // not crossing 12 or crossing 12 once and ending before or on begin1
        boolean crossed12OClock = false;
        if (endTime1Local.isBefore(openTime1Local)) { // to go forward to end1 we are crossing 12 o’clock
            crossed12OClock = true;
        }
        if (openTime2Local.isBefore(endTime1Local)) {
            if (crossed12OClock) {
                // crossing 12 for the second time;
                // intervals cannot be in non-overlapping order
                return true;
            }
            crossed12OClock = true;
        }
        if (endTime2Local.isBefore(openTime2Local)) {
            if (crossed12OClock) {
                return true;
            }
            crossed12OClock = true;
        }
        if (crossed12OClock) {
            return endTime2Local.isAfter(openTime1Local);
        } else {
            return false;
        }
    }
    public static boolean is12HoursOrLonger(Date begin, Date end) {

        java.sql.Time sqlTimeEnd = new Time(end.getTime());
        java.sql.Time sqlTimeOpen = new Time(begin.getTime());

        LocalTime endTimeLocal = sqlTimeEnd.toLocalTime();

        LocalTime openTimeLocal = sqlTimeOpen.toLocalTime();

        Duration length = Duration.between(openTimeLocal, endTimeLocal);
        if (length.isNegative()) {
            length = length.plusDays(1);
        }
        return ! length.minusHours(12).isNegative();
    }

    private static LocalTime toAm(LocalTime time) {
        return time.with(ChronoField.AMPM_OF_DAY, 0);
    }
}