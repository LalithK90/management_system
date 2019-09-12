package lk.imms.management_system.asset.offenders.entity;

import lk.imms.management_system.asset.offenders.entity.Enum.OffenderDescriptionType;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class OffenderDescription extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private OffenderDescriptionType offenderDescriptionType;

    private String description;

    @ManyToOne( fetch = FetchType.EAGER )
    private Offender offender;

}
