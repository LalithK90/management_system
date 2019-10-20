package lk.imms.management_system.asset.workingPlace.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Province {
    WP("Western Province"),
    CP("Central Province"),
    NW("North Western Province"),
    UP("Uva Province"),
    ST("Southern Province"),
    SP("Sabaragamuwa Province"),
    NC("North Central Province"),
    NP("Northern Province"),
    EP("Eastern Province"),
    DCL("DC-Law Enforcement");

    private final String province;
}
