package lk.imms.management_system.asset.offenders.entity.Enum;

import lombok.Getter;

@Getter
public enum OffenderDescriptionType {
    HEIGHT("Height"),
    SPECIAL("Special Mark"),
    FACEMARK("Face mark");

    private final String offenderDescriptionType;

    OffenderDescriptionType(String offenderDescriptionType) {
        this.offenderDescriptionType = offenderDescriptionType;
    }
}
