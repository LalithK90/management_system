package lk.imms.management_system.asset.employee.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.imms.management_system.asset.commonAsset.model.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.model.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.model.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.model.Enum.Title;
import lk.imms.management_system.asset.commonAsset.model.FileInfo;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeamMember;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@JsonFilter( "Employee" )
public class Employee extends AuditEntity {

    @Column( unique = true )
    private String payRoleNumber;

    @Size( min = 5, message = "Your name cannot be accepted" )
    private String name;

    @Size( min = 5, message = "At least 5 characters should be included calling name" )
    private String callingName;

    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    @Column( unique = true )
    private String nic;

    @Column( unique = true )
    private String departmentIdNumber;

    @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
    private String mobileOne;

    private String mobileTwo;

    private String land;

    @Column( unique = true)
    private String email;

    @Column( unique = true)
    private String officeEmail;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255)
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

    @ManyToOne( cascade = CascadeType.PERSIST)
    private WorkingPlace workingPlace;

    @OneToMany( mappedBy = "employee", fetch = FetchType.EAGER )
    @Fetch( value = FetchMode.SUBSELECT )
    private List< DetectionTeamMember > detectionTeamMembers;

    @OneToMany( mappedBy = "employee" )
    private List< EmployeeWorkingPlaceHistory > employeeWorkingHistories;

    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();

    @Transient
    private List< FileInfo > fileInfos = new ArrayList<>();

}
