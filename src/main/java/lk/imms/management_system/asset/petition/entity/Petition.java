package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionPriority;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
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
public class Petition extends AuditEntity {

    private String petitionNumber;

    private String indexNumber;

    private String village;

    private String agaDivision;

    @Column( columnDefinition = "VARCHAR(20000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String subject;

    @Enumerated( EnumType.STRING )
    private PetitionType petitionType;

    @Enumerated( EnumType.STRING )
    private PetitionPriority petitionPriority;

    @ManyToOne
    private Petitioner petitioner;

    @ManyToOne
    private WorkingPlace workingPlace;

    @OneToMany( mappedBy = "petition")
    private List< PetitionState > petitionStates;

    @OneToMany( mappedBy = "petition")
    private List< MinutePetition > minutePetitions;

    @OneToMany( mappedBy = "petition" )
    private List< DetectionTeam > detectionTeams;

    @OneToMany(mappedBy ="petition" )
    private List< PetitionOffender > petitionOffenders;

    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();


}
