package lk.imms.management_system.asset.employee.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {

    CGE("Commissioner General Of Excise"),
    ACGE("Additional Commissioner General Of Excise"),
    CE("Commissioner Of Excise"),
    DCGEL("Deputy Commissioner General Of Excise( Legal )"),
    DCGELE("Deputy Commissioner General Of Excise(Law Enforcement)"),
    ACE("Assistant Commissioner Of Excise"),
    SE("Superintendent Of Excise"),
    CIE("Chief Inspector Of Excise"),
    IE("Inspector Of Excise"),
    ESM("Excise Sergeant Major"),
    ES("Excise Sergeant"),
    EC("Excise Corporal"),
    EG("Excise Guard"),
    ED("Excise Driver");

    private final String designation;
}
