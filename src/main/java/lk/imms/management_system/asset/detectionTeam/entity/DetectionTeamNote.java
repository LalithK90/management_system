package lk.imms.management_system.asset.detectionTeam.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class DetectionTeamNote extends AuditEntity {
    @Column( columnDefinition = "VARCHAR(20000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String note;

    @ManyToOne
    private DetectionTeam detectionTeam;
}
