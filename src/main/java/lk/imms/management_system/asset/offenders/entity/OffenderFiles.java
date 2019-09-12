package lk.imms.management_system.asset.offenders.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class OffenderFiles extends AuditEntity {

    private String fileName;
    private String modifiedFileName;
    private String fileExtension;

    @ManyToOne
    private Offender offender;

    public OffenderFiles(String fileName, String modifiedFileName, String fileExtension) {
        this.fileName = fileName;
        this.modifiedFileName = modifiedFileName;
        this.fileExtension = fileExtension;
    }
}
