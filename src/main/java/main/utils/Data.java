package main.utils;

import java.time.LocalDate;

public class Data {
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr);
    }

    public static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static int dateDiff(LocalDate start, LocalDate end) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
    }
}
