package lk.imms.management_system.resources.department.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.general.commonEnum.BloodGroup;
import lk.imms.management_system.general.commonEnum.CivilStatus;
import lk.imms.management_system.general.commonEnum.Gender;
import lk.imms.management_system.general.commonEnum.Title;
import lk.imms.management_system.resources.department.entity.Enum.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Employee {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;

    @NotNull( message = "Pay Role Number is required" )
    @UniqueElements
    private String payRoleNumber;

    private String departmentNumber;

    @Enumerated( EnumType.STRING )
    private Title title;

    @Size( min = 5, message = "Your name cannot be accept" )
    private String name;

    @Size( min = 5, message = "At least 5 characters should be include calling name" )
    private String callingName;

    @Enumerated( EnumType.STRING )
    private Gender gender;

    @Enumerated( EnumType.STRING )
    private BloodGroup bloodGroup;

    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    private String nic;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfBirth;

    @Enumerated( EnumType.STRING )
    private CivilStatus civilStatus;


    @Size( min = 9, message = "Can not accept this mobile number" )
    private String mobile;

    private String land;

    @Size( min = 5, message = "Should be need to provide valid address !!" )
    private String address;

    @Enumerated( EnumType.STRING )
    private EmployeeStatus employeeStatus;

    @OneToMany
    private List< Designation > designation;

    /*private WorkingHistory workingHistory;*/

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate createdAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate updatedAt;

    //todo -> phone number email fax employee working place manytomany

}
