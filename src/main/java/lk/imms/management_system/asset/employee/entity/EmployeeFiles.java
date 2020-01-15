package lk.imms.management_system.asset.employee.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFiles extends AuditEntity {

    private String name, mimeType,newName;

    @Column(unique = true)
    private String newId;

    @Lob
    private byte[] pic;

    public EmployeeFiles(String name, String mimeType, byte[] pic, String newName,String newId) {
        this.name = name;
        this.mimeType = mimeType;
        this.pic = pic;
        this.newName = newName;
        this.newId = newId;
    }

    @ManyToOne
    private Employee employee;

}
