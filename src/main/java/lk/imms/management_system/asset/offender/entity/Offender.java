package lk.imms.management_system.asset.offender.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.imms.management_system.asset.OffednerGuardian.entity.Guardian;
import lk.imms.management_system.asset.commonAsset.model.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.model.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.model.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.model.FileInfo;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
@JsonFilter( "Offender" )
public class Offender extends AuditEntity {

    @Column( unique = true )
    private String offenderRegisterNumber;

    @NotNull
    @Size( min = 5, message = "Name (English) cannot be accepted" )
    private String nameEnglish;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL",length = 255)
    private String nameSinhala;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255)
    private String nameTamil;

    @Column( unique = true )
    private String nic;

    @Column( unique = true )
    private String passportNumber;

    @Column( unique = true )
    private String drivingLicenceNumber;

    private String mobileOne;

    private String mobileTwo;

    private String land;

    @Column( unique = true )
    private String email;

    private String address;

    @Column( columnDefinition = "VARCHAR(20000) CHARACTER SET utf8 COLLATE utf8_bin NULL",length = 20000)
    private String description;

    @Enumerated( EnumType.STRING )
    private Gender gender;

    @Enumerated( EnumType.STRING )
    private BloodGroup bloodGroup;

    @Enumerated( EnumType.STRING )
    private CivilStatus civilStatus;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfBirth;

    @OneToMany( mappedBy = "offender", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List< OffenderCallingName > offenderCallingNames;

    @OneToMany(mappedBy = "offender" )
    private List< PetitionOffender > petitionOffenders;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable( name = "offender_guardian",
            joinColumns = @JoinColumn( name = "offender_id" ),
            inverseJoinColumns = @JoinColumn( name = "guardian_id" ) )
    @Fetch( value = FetchMode.SUBSELECT )
    private List< Guardian > guardians;

    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();

    @Transient
    private List< FileInfo > fileInfos = new ArrayList<>();

    @Transient
    private String age;
}
