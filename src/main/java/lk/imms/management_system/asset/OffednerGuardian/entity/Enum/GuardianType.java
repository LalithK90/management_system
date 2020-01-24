package lk.imms.management_system.asset.OffednerGuardian.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuardianType {
    MOTHER("Mother"),
    FATHER("Father"),
    WIFE("Wife"),
    GUARDIAN("Guardian"),
    HUSBAND("Husband");

    private final String guardianType;
}
