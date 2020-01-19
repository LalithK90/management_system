package lk.imms.management_system.asset.petitioner.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionerType;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
@JsonFilter("Petitioner")
public class Petitioner extends AuditEntity {

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String nameSinhala;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String nameTamil;

    @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String nameEnglish;

    @Column( columnDefinition = "VARCHAR(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )

    private String address;

    private String mobileOne;

    private String mobileTwo;

    private String land;

    @Email( message = "Provide valid email" )
    @Column( unique = true )
    private String email;

    @Enumerated( EnumType.STRING )
    private PetitionerType petitionerType = PetitionerType.OTHER;

    @OneToMany(mappedBy = "petitioner")
    private List< Petition > petitions;

}
