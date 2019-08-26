package lk.imms.management_system.asset.offenders.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuardianType {
    MOTHER("Mother"),
    FATHER("Father"),
    WIFE("Wife"),
    GUARDIAN("Guardian");

    private final String guardianType;
}
