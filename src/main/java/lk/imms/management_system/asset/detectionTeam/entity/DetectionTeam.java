package lk.imms.management_system.asset.detectionTeam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.detectionTeam.entity.Enum.DetectionTeamStatus;
import lk.imms.management_system.asset.detectionTeam.entity.Enum.TeamAcceptation;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class DetectionTeam extends AuditEntity {

    private String number;

    private String name;


    @Enumerated( EnumType.STRING )
    private TeamAcceptation teamAcceptation;

    private LocalDateTime teamAcceptedDateTime;

    @Enumerated( EnumType.STRING )
    private DetectionTeamStatus detectionTeamStatus;

    @ManyToOne
    private Petition petition;

    @OneToMany( mappedBy = "detectionTeam", cascade = CascadeType.ALL, orphanRemoval = true )
    private List< DetectionTeamMember > detectionTeamMembers;

    @OneToMany( mappedBy = "detectionTeam", cascade = CascadeType.ALL, orphanRemoval = true )
    private List< DetectionTeamNote > detectionTeamNotes;

    @OneToMany( mappedBy = "detectionTeam" )
    private List< Crime > crimes;

}
