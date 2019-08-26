package lk.imms.management_system.asset.department.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingPlaceChangeReason {
    //todo need more reason to set this.
    MANAGER("Manager");

    private final String workingPlaceChangeReason;


}
