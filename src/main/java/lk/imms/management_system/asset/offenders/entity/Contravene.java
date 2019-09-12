package lk.imms.management_system.asset.offenders.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class Contravene extends AuditEntity {

    @NotNull
    private String contravene;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "offender_contravene",
            joinColumns = @JoinColumn(name = "contravene_id"),
            inverseJoinColumns = @JoinColumn(name = "offender_id"))
    private List<Offender> offenders;

}
