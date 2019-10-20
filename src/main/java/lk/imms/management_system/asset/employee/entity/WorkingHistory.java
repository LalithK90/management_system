package lk.imms.management_system.asset.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.employee.entity.Enum.WorkingPlaceChangeReason;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
@JsonIgnoreProperties( value = "transferDateAt", allowGetters = true )
public class WorkingHistory extends AuditEntity {


    private String remarks;

    @Enumerated( EnumType.STRING )
    private WorkingPlaceChangeReason workingPlaceChangeReason;


    //@ManyToMany( fetch = FetchType.EAGER )// give error java not like to collect data eager from so many object together
    @ManyToMany
    @LazyCollection( LazyCollectionOption.FALSE)
    @JoinTable( name = "working_history_working_place",
            joinColumns = @JoinColumn( name = "working_history_id" ),
            inverseJoinColumns = @JoinColumn( name = "working_place_id" ) )
    private List< WorkingPlace > workingPlaces;


}
