package lk.imms.management_system.asset.detection.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetectionTeamMemberRole {
    INCHARGE("In charge"),
    DRIVER("Driver"),
    MEMBER("Member");

    private final String detectionTeamMemberRole;

}
