package lk.imms.management_system.asset.offenders.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.offenders.entity.Enum.OffenderDescriptionType;
import lk.imms.management_system.general.security.entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties( value = {"createdAt", "updatedAt"}, allowGetters = true )
public class OffenderDescription {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Long id;

    @Enumerated( EnumType.STRING )
    private OffenderDescriptionType offenderDescriptionType;

    private String description;

    @ManyToOne( fetch = FetchType.EAGER )
    private Offender offender;

    @ManyToOne
    private User createdUser;

    @ManyToOne
    private User updatedUser;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate createdAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate updatedAt;
}
