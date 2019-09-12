package lk.imms.management_system.asset.petition.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PetitionFiles {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String fileName;
    private String modifiedFileName;
    private String fileExtension;

    @ManyToOne
    private Petition petition;
}
