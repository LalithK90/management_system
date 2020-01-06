package lk.imms.management_system.asset.contravene.entity;


import lk.imms.management_system.asset.offender.entity.Offender;
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

    @Column( columnDefinition = "VARCHAR(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String detail;

    @Column(unique = true)
    @NotNull
    private String code;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "offender_contravene",
            joinColumns = @JoinColumn(name = "contravene_id"),
            inverseJoinColumns = @JoinColumn(name = "offender_id"))
    private List< Offender > offenders;

}
