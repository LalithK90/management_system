package lk.imms.management_system.asset.workingPlace.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingPlaceType {
    AC("Assistant Commissioner of Excise"),
    SE("Superintend of Excise"),
    ST("Station"),
    SO("Special Operation Bureau"),
    NA("Narcotics"),
    HO("Head Office"),
    DLE("Deputy Commissioner Law Enforement");


    private final String workingPlaceType;
}
