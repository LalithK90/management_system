package lk.imms.management_system.asset.petition.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetitionerType {
    RESIDENT("By President Office"),
    RRIMEMINISTER("By Prime Minister Office"),
    NDDCA("By NDDCA"),
    OTHER("By Other Autho");

    private final String petitionerType;
}
