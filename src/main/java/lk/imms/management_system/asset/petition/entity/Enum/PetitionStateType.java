package lk.imms.management_system.asset.petition.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetitionStateType {
    FURTHERADVICEECG("Further Advice ECG"),
    FORWARDACTIONDC("Forward Action DC"),
    FORWARDACTIONAC("Forward Action AC"),
    FORWARDACTIONSE("Forward Action SE"),
    FORWARDACTIONOIC("Forward Action OIC"),
    TODETECT("To Detect"),
    DETECTED("Detected"),
    COURT("Court"),
    CHANGEDATE("Change date in court"),
    COURTCASEFINISHED("Court case finished"),
    REQUESTTOCLOSECE("Request to closed SE"),
    REQUESTTOCLOSEAC("Request to closed AC"),
    REQUESTTOCLOSEDC("Request to closed DC"),
    REQUESTTOCLOSEECG("Request to closed ECG"),
    RECEIVED("Received"),
    REJECT("Reject"),
    ACCEPT("Accept");

    private final String petitionStateType;
}
