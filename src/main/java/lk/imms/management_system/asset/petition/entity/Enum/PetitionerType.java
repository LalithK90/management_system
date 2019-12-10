package lk.imms.management_system.asset.petition.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetitionerType {
    PRESIDENT("President Office"),
    RRIMEMINISTER("Prime Minister Office"),
    NDDCA("NDDCA"),
    OTHER("Other");

    private final String petitionerType;
}
