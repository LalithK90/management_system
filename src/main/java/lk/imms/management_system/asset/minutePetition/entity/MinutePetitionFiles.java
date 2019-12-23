package lk.imms.management_system.asset.minutePetition.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinutePetitionFiles extends AuditEntity {

    private String name, mimeType,newName;

    @Column(unique = true)
    private String newId;

    @Lob
    private byte[] pic;

    public MinutePetitionFiles(String name, String mimeType, byte[] pic, String newName,String newId) {
        this.name = name;
        this.mimeType = mimeType;
        this.pic = pic;
        this.newName = newName;
        this.newId = newId;
    }


    @ManyToOne
    private MinutePetition minutePetition;
}