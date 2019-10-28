package lk.imms.management_system.asset.employee.entity;

import lk.imms.management_system.asset.commonAsset.entity.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Title;
import lk.imms.management_system.asset.detection.entity.DetectionTeamMember;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
@ToString
public class Employee extends AuditEntity {

    @NotNull( message = "Pay roll number is required" )
    @Column( unique = true )
    private String payRoleNumber;

    @Size( min = 5, message = "Your name cannot be accept" )
    private String name;

    @Size( min = 5, message = "At least 5 characters should be include calling name" )
    private String callingName;

    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    @Column( unique = true )
    private String nic;

    @Column( unique = true )
    private String departmentIdNumber;

    @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
    private String mobileOne;

    @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
    private String mobileTwo;

    @Size( max = 10, message = "Land number length should be contained 10 and 9" )
    private String land;

    @Email( message = "Provide valid email" )
    @Column( unique = true )
    private String email;

    @Email( message = "Provide valid office email" )
    @Column( unique = true )
    private String officeEmail;

    private String address;

    @Enumerated( EnumType.STRING )
    private Title title;

    @Enumerated( EnumType.STRING )
    private Gender gender;

    @Enumerated( EnumType.STRING )
    private BloodGroup bloodGroup;

    @Enumerated( EnumType.STRING )
    private Designation designation;

    @Enumerated( EnumType.STRING )
    private CivilStatus civilStatus;

    @Enumerated( EnumType.STRING )
    private EmployeeStatus employeeStatus;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfBirth;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfAssignment;

    @OneToMany( mappedBy = "employee", fetch = FetchType.EAGER )
    @Fetch( value = FetchMode.SUBSELECT )
    private List< DetectionTeamMember > detectionTeamMembers;

    @OneToMany( mappedBy = "employee", fetch = FetchType.EAGER )
    private List< EmployeeWorkingPlaceHistory > employeeWorkingHistories;

    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();

}
