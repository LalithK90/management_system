package lk.imms.management_system.asset.department.entity;

import lk.imms.management_system.asset.department.entity.Enum.ContactType;
import lk.imms.management_system.general.security.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeContactDetail {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;

    @Enumerated( EnumType.STRING )
    private ContactType contactType;

    private String contactDetails;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private User createdUser;
}
