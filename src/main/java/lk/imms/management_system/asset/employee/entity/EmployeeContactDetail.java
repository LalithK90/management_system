package lk.imms.management_system.asset.employee.entity;

import lk.imms.management_system.asset.employee.entity.Enum.ContactType;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class EmployeeContactDetail extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private ContactType contactType;

    private String contactDetails;

    @ManyToOne
    private Employee employee;

}
