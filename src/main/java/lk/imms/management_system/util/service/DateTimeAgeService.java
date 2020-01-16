package lk.imms.management_system.util.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class DateTimeAgeService {

    public String dateDifference(LocalDate from, LocalDate to) {
        Period difference = Period.between(from, to);
        return difference.getYears() + " Years, " + difference.getMonths() + " Months, " + difference.getDays() + " " +
                "Days";
    }

    public LocalDate getPastDateByMonth(int month) {
        return LocalDate.now().minusMonths(month);
    }

    public LocalDate getFutureDateByMonth(int month) {
        return LocalDate.now().plusMonths(month);
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public int getAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return Period.between(dateOfBirth, today).getYears();
    }

    public String getAgeString(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        int year = Period.between(dateOfBirth, today).getYears();
        return String.valueOf(year).concat(" Years");
    }

    /* Converting the LocalDate to LocalDateTime using atStartOfDay() method. This method adds midnight time (start
    of the day time) with the local date.     */
    public LocalDateTime dateTimeToLocalDateStartInDay(LocalDate localDate) {
        return localDate.atStartOfDay();
    }
    /* atTime(int hour, int minutes, int seconds, int nanoseconds)
     * hour - the hour-of-day, value range from 0 to 23.
     * minute - the minute-of-hour, value range from 0 to 59.
     * second - the second-of-minute, value range from 0 to 59.
     * nanoOfSecond - the nano-of-second, value range from 0 to 999,999,999
     */
    public LocalDateTime dateTimeToLocalDateEndInDay(LocalDate localDate) {
        return localDate.atTime(21,59, 59, 999999999);
    }
}
