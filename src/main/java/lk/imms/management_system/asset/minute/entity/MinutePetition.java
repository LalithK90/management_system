package lk.imms.management_system.asset.minute.entity;

import lk.imms.management_system.asset.minute.entity.Enum.MinuteState;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class MinutePetition extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private MinuteState minuteState;

    @ManyToOne
    private Minute minute;

    @ManyToOne
    private Petition petition;
}
