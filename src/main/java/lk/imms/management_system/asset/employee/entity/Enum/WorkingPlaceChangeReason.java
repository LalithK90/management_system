package lk.imms.management_system.asset.employee.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingPlaceChangeReason {
    IMPORTANCEOFSERVICE("Importance of Service"),
    EMLOYEEREQUEST("Employee Request"),
    DISIPILINARYACTION("Disciplinary Action"),
    ANNURALTRANSFER("Annual Transfer");

    private final String workingPlaceChangeReason;

}
