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
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
@JsonFilter( "WorkingPlace" )
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

    private String landOne;

    private String landTwo;

    private String landThree;

    private String landFour;

    private String faxNumber;

    @Email( message = "Provide valid email" )
    @Column( unique = true )
    private String emailOne;

    private String emailTwo;

    @OneToMany( mappedBy = "workingPlace", fetch = FetchType.EAGER )
    private List< EmployeeWorkingPlaceHistory > employeeWorkingHistories;

    @ManyToMany( mappedBy = "workingPlaces", fetch = FetchType.EAGER )
    private Set< User > users;

}
