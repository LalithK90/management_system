package lk.imms.management_system.asset.employee.entity;

import lk.imms.management_system.asset.commonAsset.entity.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Title;
import lk.imms.management_system.asset.detection.entity.DetectionTeamMember;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.UniqueElements;
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
public class Employee extends AuditEntity {

    @NotNull( message = "Employee number is required" )
    @UniqueElements
    private String employeeNumber;

    @NotNull( message = "Pay roll number is required" )
    @UniqueElements
    private String payRoleNumber;

    @Size( min = 5, message = "Your name cannot be accept" )
    private String name;

    @Size( min = 5, message = "At least 5 characters should be include calling name" )
    private String callingName;

    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    private String nic;

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

    @ManyToOne
    private Designation designation;


    @OneToMany( mappedBy = "employee", fetch = FetchType.EAGER )
    @Fetch( value = FetchMode.SUBSELECT )
    private List< EmployeeContactDetail > employeeContactDetail;

    @OneToMany( mappedBy = "employee", fetch = FetchType.EAGER )
    @Fetch( value = FetchMode.SUBSELECT )
    private List< DetectionTeamMember > detectionTeamMembers;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable( name = "employee_working_history",
            joinColumns = @JoinColumn( name = "employee_id" ),
            inverseJoinColumns = @JoinColumn( name = "working_history_id" ) )
    private List< WorkingHistory > workingHistories;

    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();

}
