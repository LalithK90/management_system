package lk.imms.management_system.asset.commonAsset.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum District {
    /*Western */
    CB("Colombo"),
    GP("Gampaha"),
    KT("Kaluthara"),
    /*North*/
    JF("Jaffna"),
    KI("Kilinochchiya"),
    VA("Vauniyava"),
    MV("Mulative"),
    MA("Mannar"),
    /*Southern*/
    GL("Galle"),
    MR("Mattar"),
    HT("Hambantota"),
    /*Eastern*/
    AM("Ampara"),
    BT("Batticlo"),
    TM("Trincomale"),
    /*North central*/
    AP("Anuradhapura"),
    PN("Polonnaruwa"),
    /*Central*/
    KD("Kandy"),
    NE("Nuwara Eliya"),
    MT("Matale"),
    /*North west*/
    KN("Kurunrgala"),
    PT("Puttlam"),
    /*Uva*/
    BU("Badulla"),
    MN("Monaragala"),
    /*Sabaragamuwa*/
    RP("Rathnapura"),
    KG("Kegalle");

    private final String district;
}
