package lk.imms.management_system.asset.offenders.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.department.entity.Enum.EmployeeStatus;
import lk.imms.management_system.general.commonEnum.BloodGroup;
import lk.imms.management_system.general.commonEnum.CivilStatus;
import lk.imms.management_system.general.commonEnum.Gender;
import lk.imms.management_system.general.commonEnum.Title;
import lk.imms.management_system.general.security.entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties( value = {"createdAt", "updatedAt"}, allowGetters = true )
public class Offender {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;

    private String offenderRegisterNumber;

    @Size( min = 5, message = "Name (English) cannot be accept" )
    private String nameEnglish;

    @Size( min = 5, message = "Name (Sinhala) cannot be accept" )
    private String nameSinhala;

    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    private String nic;

    private String passportNumber;

    private String drivingLicenceNumber;

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

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate createdAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate updatedAt;

    @ManyToOne
    private User createdUser;

    @ManyToOne
    private User updatedUser;

    @ManyToOne( fetch = FetchType.EAGER )
    private OffenderContactDetail offenderContactDetail;

    @OneToMany(mappedBy = "offender")
    private List< OffenderCallingName > offenderCallingNames;

    @OneToMany(mappedBy = "offender")
    private List<OffenderDescription> offenderDescriptions;

    @OneToMany(mappedBy = "offender")
    private List<Guardian> guardians;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "offender_contravene",
            joinColumns = @JoinColumn(name = "offender_id"),
            inverseJoinColumns = @JoinColumn(name = "contravene_id"))
    private List<Contravene> contravenes;

}
