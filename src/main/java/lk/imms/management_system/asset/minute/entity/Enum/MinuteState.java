package lk.imms.management_system.asset.minute.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MinuteState {
    REQUSETADVICE("Request advice"),
    CURRENTSITUATION("Current Situation"),
    FORWARDACTION("Forward Action");

    private final String minuteState;
}
