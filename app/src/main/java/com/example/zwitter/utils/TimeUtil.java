package com.example.zwitter.utils;

public class TimeUtil {
    public String getTimeValue(long current, long old) {
        long diff = current - old;

        if (getDiffInDays(diff) > 0)
            return getDiffInDays(diff) + "d";
        else if (getDiffInHours(diff) > 0)
            return getDiffInDays(diff)+"h";
        else if (getDiffInMinutes(diff ) > 0)
            return getDiffInMinutes(diff)+"m";
        else
            return getDiffInSeconds(diff)+"s";

    }

    private long getDiffInSeconds(long diff) {
        return diff / 1000 % 60;
    }

    private long getDiffInMinutes(long diff) {
        return diff / (60 * 1000) % 60;
    }

    private long getDiffInHours(long diff) {
        return diff / (60 * 60 * 1000) % 24;
    }

    private long getDiffInDays(long diff) {
        return diff / (24 * 60 * 60 * 1000);
    }
}