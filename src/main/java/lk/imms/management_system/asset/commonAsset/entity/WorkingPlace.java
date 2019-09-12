package lk.imms.management_system.asset.commonAsset.entity;

import lk.imms.management_system.asset.employee.entity.WorkingHistory;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WorkingPlace {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;

    @NotNull( message = "Name is required" )
    @UniqueElements
    private String name;
//todo court place
    @UniqueElements
    private String code;

    @OneToMany(mappedBy = "workingPlace")
    private List<WorkingPlaceContactDetail> workingPlaceContactDetails;

    @ManyToMany( mappedBy = "workingPlaces" )
    private List< WorkingHistory > workingHistory;

}
