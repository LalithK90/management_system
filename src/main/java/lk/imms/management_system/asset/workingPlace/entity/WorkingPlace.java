package lk.imms.management_system.asset.workingPlace.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.imms.management_system.asset.employee.entity.EmployeeWorkingPlaceHistory;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.workingPlace.entity.Enum.District;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.Enum.WorkingPlaceType;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
@JsonFilter("WorkingPlace")
public class WorkingPlace extends AuditEntity {

    @NotNull( message = "Name is required" )
    private String name;

    @Enumerated( EnumType.STRING )
    private Province province;

    @Enumerated( EnumType.STRING )
    private District district;

    @Enumerated( EnumType.STRING )
    private WorkingPlaceType workingPlaceType;

    @Column( unique = true, nullable = false )
    private String code;

    @Column( nullable = false )
    private String address;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 or 9 \n At least one phone number " +
            "should be in on working place" )
    private String landOne;

    @Size( max = 10, message = "Land number length should be contained 10 or 9" )
    private String landTwo;

    @Size( max = 10, message = "Land number length should be contained 10 or 9" )
    private String landThree;

    @Size( max = 10, message = "Land number length should be contained 10 or 9" )
    private String landFour;

    @Size( max = 10, message = "Fax number length should be contained 10 or 9" )
    private String faxNumber;

    @Email( message = "Provide valid email" )
    @Column( unique = true )
    private String emailOne;

    @Email( message = "Provide valid email" )
    @Column( unique = true)
    private String emailTwo;

    @OneToMany( mappedBy = "workingPlace", fetch = FetchType.EAGER )
    private List< EmployeeWorkingPlaceHistory > employeeWorkingHistories;

    @ManyToMany( mappedBy = "workingPlaces" )
    private List< User > users;

}
