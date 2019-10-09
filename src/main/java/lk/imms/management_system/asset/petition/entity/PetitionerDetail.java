package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class PetitionerDetail extends AuditEntity {

    private String name;

    private String address;

    @Size( max = 10, min = 9, message = "Mobile number length should be contained 10 and 9" )
    private String mobileOne;

    @Size( max = 10, min = 9, message = "Mobile number length should be contained 10 and 9" )
    private String mobileTwo;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 and 9" )
    private String land;

    @Email(message = "Provide valid email")
    @Column(unique = true)
    private String email;


}
