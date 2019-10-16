package lk.imms.management_system.asset.userManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.imms.management_system.asset.commonAsset.entity.WorkingPlace;
import lk.imms.management_system.asset.employee.entity.Employee;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = "createdDate", allowGetters = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    @Size(min = 5, message = "user name should include at least five characters")
    private String username;

    @Column(nullable = false)
    @Size(min = 4, message = "password should include four characters or symbols")
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch( FetchMode.SUBSELECT)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch( FetchMode.SUBSELECT)
    @JoinTable(name = "user_working_place",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "working_place_id"))
    private List< WorkingPlace > workingPlaces;

}
