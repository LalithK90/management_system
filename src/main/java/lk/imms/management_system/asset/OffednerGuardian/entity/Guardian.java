package lk.imms.management_system.asset.OffednerGuardian.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.OffednerGuardian.entity.Enum.GuardianType;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class Guardian extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private GuardianType guardianType;

    private String name;


    @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
    @Column(unique = true)
    private String nic;

    @Column( columnDefinition = "VARCHAR(20000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String remark;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable( name = "offender_guardian",
            joinColumns = @JoinColumn( name = "guardian_id" ),
            inverseJoinColumns = @JoinColumn( name = "offender_id" ) )
    @Fetch( value = FetchMode.SUBSELECT )
    private List<Offender> offenders;



}
