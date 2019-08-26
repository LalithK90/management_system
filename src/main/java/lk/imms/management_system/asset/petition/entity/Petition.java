package lk.imms.management_system.asset.petition.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.general.security.entity.User;
import lombok.*;
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
@JsonIgnoreProperties( value = {"createdAt", "updatedAt"}, allowGetters = true )
public class Petition {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;


    // -->Auto Generate Year/Month/OfficeType/StationCode/PetitionNumberFromDB
    private String petitionNumber;

    private String indexNumber;

    @Enumerated( EnumType.STRING )
    private PetitionType petitionType;

    @OneToMany( mappedBy = "petition" )
    private List< PetitionState > petitionStates;

    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private PetitionerDetail petitionerDetail;

    @ManyToOne
    private User createdUser;

    @ManyToOne
    private User updatedUser;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate createdAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate updatedAt;


}
