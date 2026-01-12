package main.utils;


public enum ProjectState {
    TESTING,
    DEVELOPMENT,
    VERIFYING;

    private String startDate;
    private String endDate;

    public void startTestingPeriod(String startDate) {
        this.startDate = startDate;
        this.endDate = calculateEndDate();
    }

    public boolean checkPeriodActive(String currentDate, ProjectState state) {
        return state != TESTING || compareToDate(currentDate, endDate) <= 0;
    }

    public String getEndDate() {
        return endDate;
    }

    private String calculateEndDate() {
        String[] parts = startDate.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        day += 12;

        if (month == 2) {
            if (isLeapYear(year)) {
                if (day > 29) {
                    day -= 29;
                    month += 1;
                }
            } else {
                if (day > 28) {
                    day -= 28;
                    month += 1;
                }
            }
        } else if (monthHas30Days(month)) {
            if (day > 30) {
                day -= 30;
                month += 1;
            }
        } else {
            if (day > 31) {
                day -= 31;
                month += 1;
            }
        }
        if (month > 12) {
            month -= 12;
            year += 1;
        }
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private boolean monthHas30Days(int month) {
        return (month < 7 && month % 2 == 0) || (month >= 7 && month % 2 != 0);
    }

    private int compareToDate(String date1, String date2) {
        String[] parts1 = date1.split("-");
        String[] parts2 = date2.split("-");
        for (int i = 0; i < 3; i++) {
            int part1 = Integer.parseInt(parts1[i]);
            int part2 = Integer.parseInt(parts2[i]);
            if (part1 != part2) {
                return part1 - part2;
            }
        }
        return 0;
    }

}
