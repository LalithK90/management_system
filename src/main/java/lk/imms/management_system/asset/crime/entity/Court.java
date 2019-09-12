package lk.imms.management_system.asset.crime.entity;

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
public class Court extends AuditEntity {
    private String name;

    private String place;

    @OneToMany( mappedBy = "court" )
    private List< Crime > crimes;

}
