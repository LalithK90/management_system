package lk.imms.management_system.asset.department.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.commonAsset.entity.WorkingPlace;
import lk.imms.management_system.asset.department.entity.Enum.WorkingPlaceChangeReason;
import lk.imms.management_system.general.security.entity.User;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties( value = {"createdAt", "updatedAt", "transferDateAt"}, allowGetters = true )
public class WorkingHistory {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;

    private String remarks;

    @Enumerated( EnumType.STRING )
    private WorkingPlaceChangeReason workingPlaceChangeReason;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate createdAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate updatedAt;

    @ManyToOne
    private User dataEnterUser;

    //@ManyToMany( fetch = FetchType.EAGER )// give error java not like to collect data eager from so many object together
    @ManyToMany
    @LazyCollection( LazyCollectionOption.FALSE)
    @JoinTable( name = "working_history_working_place",
            joinColumns = @JoinColumn( name = "working_history_id" ),
            inverseJoinColumns = @JoinColumn( name = "working_place_id" ) )
    private List< WorkingPlace > workingPlaces;


}
