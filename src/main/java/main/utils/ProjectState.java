package main.utils;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum ProjectState {
    TESTING,
    DEVELOPMENT,
    VERIFYING;

    private LocalDate startDate;
    private LocalDate endDate;


    public void startTestingPeriod(String startDate) {
        this.startDate = LocalDate.parse(startDate);
        this.endDate = this.startDate.plusDays(12);
        System.out.println("Testing period started on " + this.startDate + " and will end on " + this.endDate);
    }

    public boolean updateProjectState(String currentDate) {
        long daysBetween =  ChronoUnit.DAYS.between(this.startDate, LocalDate.parse(currentDate)) + 1;
        if (daysBetween > 12) {
            this.startDate = this.endDate;
            this.endDate = this.startDate.plusDays(120);
            return true;
        }
        return false;
    }

}
