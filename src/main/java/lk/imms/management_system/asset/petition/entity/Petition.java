package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
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

    // -->Auto Generate Year/Month/OfficeType/StationCode/PetitionNumberFromDB
    private String petitionNumber;

    private String indexNumber;
    @Column(length = 50000)
    private String subject;

    @Enumerated( EnumType.STRING )
    private PetitionType petitionType;

    @OneToMany( mappedBy = "petition" )
    private List< PetitionState > petitionStates;

    @OneToMany(mappedBy = "petition")
    private List< MinutePetition > minutePetitions;

    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private PetitionerDetail petitionerDetail;



    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();

}
