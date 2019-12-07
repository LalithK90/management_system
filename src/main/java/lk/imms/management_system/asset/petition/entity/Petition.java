package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionPriority;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
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

    private String village;

    private String agaDivision;

    @Column( columnDefinition = "VARCHAR(20000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String subject;

    @Enumerated( EnumType.STRING )
    private PetitionType petitionType;

    @Enumerated( EnumType.STRING )
    private PetitionPriority petitionPriority;

    @OneToMany( mappedBy = "petition" )
    private List< PetitionState > petitionStates;

    @OneToMany( mappedBy = "petition")
    private List< MinutePetition > minutePetitions;

    @ManyToOne
    private Petitioner petitioner;

}
