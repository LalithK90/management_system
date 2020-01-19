package lk.imms.management_system.asset.employee.entity;


import lk.imms.management_system.asset.employee.entity.Enum.WorkingPlaceChangeReason;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class EmployeeWorkingPlaceHistory extends AuditEntity {

    @Column( length = 50000 )
    private String remark;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate from_place;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate to_place;

    private String workingDuration;

    @Enumerated( EnumType.STRING )
    private WorkingPlaceChangeReason workingPlaceChangeReason;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Employee employee;

    @ManyToOne
    private WorkingPlace workingPlace;


}
