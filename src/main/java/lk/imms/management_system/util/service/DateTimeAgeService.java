package lk.imms.management_system.util.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class DateTimeAgeService {
    public String dateDifference(LocalDate from, LocalDate to) {
        Period difference = Period.between(from, to);
        return difference.getYears() + " Years, " + difference.getMonths() + " Months, " + difference.getDays() + " Days";
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

}
