package lk.imms.management_system.asset.detectionTeam.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetectionTeamMemberRole {
    INCHARGE("In charge"),
    MEMBER("Member"),
    DRIVER("Driver");

    private final String detectionTeamMemberRole;

}
