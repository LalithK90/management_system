package lk.imms.management_system.asset.detection.entity;

import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.detection.entity.Enum.DetectionTeamStatus;
import lk.imms.management_system.asset.detection.entity.Enum.TeamAcceptation;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class DetectionTeam extends AuditEntity {

    @ManyToOne
    private Petition petition;

    private String name;

    @Enumerated( EnumType.STRING )
    private TeamAcceptation teamAcceptation;

    private LocalDateTime teamAcceptedDateTime;

    @Enumerated( EnumType.STRING )
    private DetectionTeamStatus detectionTeamStatus;

    @OneToMany( mappedBy = "detectionTeam" )
    private List< DetectionTeamMember > detectionTeamMembers;

    @OneToMany( mappedBy = "detectionTeam" )
    private List< Crime > crimes;


}
