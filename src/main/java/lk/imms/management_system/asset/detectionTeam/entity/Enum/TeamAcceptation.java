package lk.imms.management_system.asset.detectionTeam.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TeamAcceptation {
    YES("Accepted"),
    LESSSTAFF("Request more members"),
    NO("Not accepted");

    private final String teamAcceptation;

}
