package lk.imms.management_system.asset.detectionTeam.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetectionTeamStatus {
    SUCCESS("Successfully Completed "),
    NOTSUCCESS("Not Completed "),
    DETAILCOLLECT("Details collected ");

    private final String detectionTeamStatus;
}
