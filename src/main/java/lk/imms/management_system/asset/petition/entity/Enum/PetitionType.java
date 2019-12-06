package lk.imms.management_system.asset.petition.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetitionType {
    RESIDENT("By President Office"),
    RRIMEMINISTER("By Prime Minister Office"),
    NDDCA("By NDDCA"),
    TELEPHONE("By Telephoning"),
    LETTERS("By Letters"),
    EMAIL("By Email"),
    SMS("By SMS"),
    OTHERAUTHORITIES("By Other Autho"),
    OTHER("By Direct Handover"),
    FACEBOOK("By Facebook"),
    FORMWEB("By Web Site");

    private final String petitionType;
}
