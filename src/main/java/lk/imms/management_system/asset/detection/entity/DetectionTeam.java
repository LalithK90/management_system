package lk.imms.management_system.asset.detection.entity;

import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class DetectionTeam extends AuditEntity {

    @ManyToOne( fetch = FetchType.EAGER )
    private Petition petition;

    @OneToMany( mappedBy = "detectionTeam" )
    private List< DetectionTeamMember > detectionTeamMembers;

    @OneToMany( mappedBy = "detectionTeam" )
    private List< Crime > crimes;


}
