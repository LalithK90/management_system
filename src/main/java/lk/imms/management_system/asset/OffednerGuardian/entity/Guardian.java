package lk.imms.management_system.asset.OffednerGuardian.entity;

import lk.imms.management_system.asset.OffednerGuardian.entity.Enum.GuardianType;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class Guardian extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private GuardianType guardianType;

    private String name;

    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    @Column(unique = true)
    private String nic;

    @Column( columnDefinition = "VARCHAR(20000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String remark;

    @ManyToOne
    private Offender offender;



}
