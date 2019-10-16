package lk.imms.management_system.asset.minute.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MinutePriority {
    HG("High"),
    MD("Medium"),
    AV("Average");

    private final String minutePriority;
}
