package lk.imms.management_system.asset.petition.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetitionFiles extends AuditEntity {


    private String name, mimeType,newName;

    @Column(unique = true)
    private String newId;

    @Lob
    private byte[] pic;

    public PetitionFiles(String name, String mimeType, byte[] pic, String newName,String newId) {
        this.name = name;
        this.mimeType = mimeType;
        this.pic = pic;
        this.newName = newName;
        this.newId = newId;
    }

    @ManyToOne
    private Petition petition;
}
