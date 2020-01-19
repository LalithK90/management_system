package lk.imms.management_system.asset.petitionAddOffender.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class PetitionOffender extends AuditEntity {

    @ManyToOne
    private Petition petition;

    @ManyToOne
    private Offender offender;

    @ManyToMany( cascade = CascadeType.ALL )
    @JoinTable( name = "petition_offender_contravene",
            joinColumns = @JoinColumn( name = "petition_offender_id" ),
            inverseJoinColumns = @JoinColumn( name = "contravene_id" ) )
    private List< Contravene > contravenes;

    @Transient
    private List< Offender > offenders;

}
