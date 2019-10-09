package lk.imms.management_system.asset.commonAsset.entity;

import lk.imms.management_system.asset.commonAsset.entity.Enum.Province;
import lk.imms.management_system.asset.commonAsset.entity.Enum.WorkingPlaceType;
import lk.imms.management_system.asset.employee.entity.WorkingHistory;
import lk.imms.management_system.asset.userManagement.entity.User;
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
public class WorkingPlace extends AuditEntity {

    @NotNull( message = "Name is required" )
    @Column( unique = true )
    private String name;

    @Enumerated( EnumType.STRING )
    private Province province;

    @Enumerated( EnumType.STRING )
    private WorkingPlaceType workingPlaceType;

    @Column( unique = true )
    private String code;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 and 9" )
    private String landOne;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 and 9" )
    private String landTwo;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 and 9" )
    private String landThree;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 and 9" )
    private String landFour;

    @Size( max = 10, min = 9, message = "Fax number length should be contained 10 and 9" )
    private String faxNumber;

    @Email(message = "Provide valid email")
    @Column(unique = true)
    private String email;

    @ManyToMany( mappedBy = "workingPlaces" )
    private List< WorkingHistory > workingHistory;

    @ManyToMany( mappedBy = "workingPlaces" )
    private List< User > users;

}
