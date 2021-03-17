package com.prodnees.config.constants;

public interface TimeConstants {
    int ONE_MINUTE = 1000 * 60;
    int TWO_MINUTES = ONE_MINUTE * 2;
    int THREE_MINUTES = ONE_MINUTE * 3;
    int FOUR_MINUTES = ONE_MINUTE * 4;
    int FIVE_MINUTES = ONE_MINUTE * 5;
    int SIX_MINUTES = ONE_MINUTE * 6;
    int SEVEN_MINUTES = ONE_MINUTE * 7;
    int EIGHT_MINUTES = ONE_MINUTE * 8;
    int NINE_MINUTES = ONE_MINUTE * 9;
    int TEN_MINUTES = ONE_MINUTE * 10;
    int ONE_HOUR = TEN_MINUTES * 6;
    int TWELVE_HOURS = ONE_HOUR * 12;
    int ONE_DAY = TWELVE_HOURS * 2;
    int ONE_WEEK = ONE_DAY * 7;
    int THIRTY_DAYS = ONE_DAY * 30;

    interface Formats {
        String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
        String DATE = "yyyy-MM-dd";
        String TIME_FULL = "HH:mm:ss";
        String TIME_HOURS_MINUTES = "HH:mm";
    }
}
