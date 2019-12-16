package lk.imms.management_system.asset.detection.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetectionTeamStatus {
    SUCCESS("Successfully Completed "),
    PARSUCCESS("Partially Completed "),
    NOTSUCCESS("Not Completed "),
    NOARREST("Offender not arrest "),
    DETAILCOLLECT("Details collected "),
    LESSSTAFF("Not adequate staff ");

    private final String detectionTeamStatus;
}
