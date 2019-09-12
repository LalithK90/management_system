package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
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
public class PetitionState extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private PetitionStateType petitionStateType;

    private String remark;

    @ManyToOne
    private Petition petition;

}
