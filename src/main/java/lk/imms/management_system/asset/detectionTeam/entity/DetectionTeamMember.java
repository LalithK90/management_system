package lk.imms.management_system.asset.detectionTeam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.detectionTeam.entity.Enum.DetectionTeamMemberRole;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class DetectionTeamMember extends AuditEntity {

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private DetectionTeam detectionTeam;

    @Enumerated( EnumType.STRING )
    private DetectionTeamMemberRole detectionTeamMemberRole;

}
