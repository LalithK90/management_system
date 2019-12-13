package lk.imms.management_system.asset.minute.entity;

import lk.imms.management_system.asset.commonAsset.entity.FileInfo;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.minute.entity.Enum.MinuteState;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
@ToString
public class MinutePetition extends AuditEntity {

    @Column( columnDefinition = "VARCHAR(20000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String detail;

    @Enumerated( EnumType.STRING )
    private MinuteState minuteState;

    @ManyToOne
    private Petition petition;

    @ManyToOne( fetch = FetchType.EAGER )
    private Employee employee;

    @ManyToOne( fetch = FetchType.EAGER )
    private WorkingPlace workingPlace;

    @Transient
    private List< MultipartFile > files;

    @Transient
    private List< FileInfo > fileInfos;

    @Transient
    private PetitionStateType petitionStateType;

}
