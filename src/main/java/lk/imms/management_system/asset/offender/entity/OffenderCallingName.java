package lk.imms.management_system.asset.offender.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class OffenderCallingName extends AuditEntity {
    @NotNull
    private String callingName;

    @ManyToOne( fetch = FetchType.EAGER )
    private Offender offender;

}
