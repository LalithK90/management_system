package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class PetitionerDetail extends AuditEntity {

    private String name;

    private String address;

    @OneToMany(mappedBy = "petitionerDetail")
    private List< PetitionerContactDetail > petitionerContactDetails;


}
