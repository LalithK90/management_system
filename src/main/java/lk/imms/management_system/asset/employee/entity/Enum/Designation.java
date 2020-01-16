package lk.imms.management_system.asset.employee.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
    //All station can check
    CGE("Commissioner General Of Excise"),
    ACGE("Additional Commissioner General Of Excise"),
    CE("Commissioner Of Excise"),
    DCEL("Deputy Commissioner Of Excise( Legal )"),
    DCELE("Deputy Commissioner Excise(Law Enforcement)"),
    //Normal every thing belong to his - able to check more than one work station
    ACE("Assistant Commissioner Of Excise"),
    //Below guy has check station belong to him
    SE("Superintendent Of Excise"),
    //Station staff all below this comment
    OIC("Chief Inspector Of Excise"),
    IE("Inspector Of Excise"),
    ESM("Excise Sergeant Major"),
    ES("Excise Sergeant"),
    //There is no authority to logo in to the system
    EC("Excise Corporal"),
    EG("Excise Guard"),
    ED("Excise Driver");

    private final String designation;
}
