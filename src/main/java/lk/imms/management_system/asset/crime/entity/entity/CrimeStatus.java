package lk.imms.management_system.asset.crime.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public enum CrimeStatus {
    NO("Note Yet Completed"),
    PARTIAL("Partially Complete"),
    COMPLETED("Completed");

    private final String crimeStatus;
}
