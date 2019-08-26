package lk.imms.management_system.asset.department.entity.Enum;

import lombok.Getter;

@Getter
public enum ContactType {
    EMAIL("Email"),
    MOB("Mobile"),
    LAND("Land"),
    ADD("Address"),
    FAX("Fax");

    private final String contactType;

    ContactType(String contactType) {
        this.contactType = contactType;
    }

}
