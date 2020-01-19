package lk.imms.management_system.asset.court.entity;

import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Column;
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

    @Column(columnDefinition = "VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_bin NULL"  )
    private String name;

    @Column(columnDefinition = "VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_bin NULL"  )
    private String place;

    @OneToMany( mappedBy = "court" )
    private List< Crime > crimes;

}
