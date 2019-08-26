package lk.imms.management_system.asset.commonAsset.entity;

import lk.imms.management_system.asset.department.entity.Enum.ContactType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WorkingPlaceContactDetail {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;

    @Enumerated( EnumType.STRING )
    private ContactType contactType;

    private String contactDetails;

    @ManyToOne
    private WorkingPlace workingPlace;
}
