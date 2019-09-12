package lk.imms.management_system.asset.petition.entity;

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
public class PetitionerContactDetail extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private ContactType contactType;

    private String contactDetails;

    @ManyToOne( fetch = FetchType.EAGER )
    private PetitionerDetail petitionerDetail;

}
