package lk.imms.management_system.resources.department.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties( value = {"createdAt", "updatedAt","transferDateAt"}, allowGetters = true )
public class WorkingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotNull(message = "Designation is required")
    @UniqueElements
    private String reason;

    @ManyToMany
    @JoinTable()
    private List<WorkingPlace> workingPlace;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate startingAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate transferDateAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate createdAt;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate updatedAt;

}
