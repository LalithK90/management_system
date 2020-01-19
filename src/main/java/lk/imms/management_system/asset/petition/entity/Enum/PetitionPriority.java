package lk.imms.management_system.asset.petition.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetitionPriority {
    HG("High"),
    MD("Medium"),
    AV("Average");

    private final String petitionPriority;
}
