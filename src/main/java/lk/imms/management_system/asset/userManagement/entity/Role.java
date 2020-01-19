package lk.imms.management_system.asset.userManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Column( unique = true )
    private String roleName;

    @ManyToMany( cascade = CascadeType.ALL )
    @Fetch( FetchMode.SUBSELECT )
    @JoinTable( name = "user_role",
            joinColumns = @JoinColumn( name = "role_id" ),
            inverseJoinColumns = @JoinColumn( name = "user_id" ) )
    @OrderColumn(name = "user")
    private List< User > users;
}
