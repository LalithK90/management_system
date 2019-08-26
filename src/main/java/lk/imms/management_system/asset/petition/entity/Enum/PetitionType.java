package lk.imms.management_system.asset.petition.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetitionType {
    TELEPHONE("By Telephoning"),
    LETTERS("By Letters"),
    EMAIL("By Email"),
    SMS("By SMS"),
    FACEBOOK("By Facebook"),
    FORMWEB("By Web Site"),
    RESIDENT("By President Office"),
    RRIMEMINISTER("By Prime Minister Office"),
    NDDCA("By NDDCA"),
    OTHERAUTHORITIES("By Other Autho"),
    OTHER("By Other");

    private final String petitionType;
}
