package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
@ToString
public class Petitioner extends AuditEntity {

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String nameSinhala;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String nameTamil;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String nameEnglish;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String address;

    @Size( max = 10, min = 9, message = "Mobile number length should be contained 10 and 9" )
    private String mobileOne;

    @Size( max = 10, min = 9, message = "Mobile number length should be contained 10 and 9" )
    private String mobileTwo;

    @Size( max = 10, min = 9, message = "Land number length should be contained 10 and 9" )
    private String land;

    @Email( message = "Provide valid email" )
    @Column( unique = true )
    private String email;

    @OneToMany(mappedBy = "petitioner")
    private List<Petition> petitions;

}
