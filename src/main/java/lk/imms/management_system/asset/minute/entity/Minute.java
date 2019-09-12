package lk.imms.management_system.asset.minute.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class Minute extends AuditEntity {

    @OneToMany(mappedBy = "minute")
    private List< MinutePetition > minutePetitions;

    @Column(length = 50000)
    private String remark;

    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();


}
