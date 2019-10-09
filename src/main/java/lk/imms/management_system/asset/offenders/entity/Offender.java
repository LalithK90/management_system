package lk.imms.management_system.asset.offenders.entity;

import lk.imms.management_system.asset.commonAsset.entity.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Title;
import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
public class Offender extends AuditEntity {

    private String offenderRegisterNumber;

    @Size( min = 5, message = "Name (English) cannot be accept" )
    private String nameEnglish;

    @Size( min = 5, message = "Name (Sinhala) cannot be accept" )
    private String nameSinhala;

    @Size( min = 5, message = "Name (Tamil) cannot be accept" )
    private String nameTamil;

    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    @Column(unique = true)
    private String nic;

    @Column(unique = true)
    private String passportNumber;

    @Column(unique = true)
    private String drivingLicenceNumber;

    @Size( max = 10, min = 9, message = "Mobile number length should be contained 10 and 9" )
    private String mobileOne;

    @Size( max = 10, min = 9, message = "Mobile number length should be contained 10 and 9" )
    private String mobileTwo;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 and 9" )
    private String land;

    @Email(message = "Provide valid email")
    @Column(unique = true)
    private String email;

    @Enumerated( EnumType.STRING )
    private Title title;

    @Enumerated( EnumType.STRING )
    private Gender gender;

    @Enumerated( EnumType.STRING )
    private BloodGroup bloodGroup;

    @Enumerated( EnumType.STRING )
    private CivilStatus civilStatus;

    @Enumerated( EnumType.STRING )
    private EmployeeStatus employeeStatus;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfBirth;

    @OneToMany( mappedBy = "offender" )
    private List< OffenderCallingName > offenderCallingNames;

    @OneToMany( mappedBy = "offender" )
    private List< OffenderDescription > offenderDescriptions;

    @OneToMany( mappedBy = "offender" )
    private List< Guardian > guardians;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable( name = "offender_contravene",
            joinColumns = @JoinColumn( name = "offender_id" ),
            inverseJoinColumns = @JoinColumn( name = "contravene_id" ) )
    private List< Contravene > contravenes;

    @ManyToMany( cascade = CascadeType.ALL )
    @JoinTable( name = "crime_offender",
            joinColumns = @JoinColumn( name = "crime_id" ),
            inverseJoinColumns = @JoinColumn( name = "offender_id" ) )
    @Fetch( value = FetchMode.SUBSELECT )
    private List< Crime > crimes;


    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();

}
