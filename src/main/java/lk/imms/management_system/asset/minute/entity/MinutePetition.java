package lk.imms.management_system.asset.minute.entity;

import lk.imms.management_system.asset.minute.entity.Enum.MinuteState;
import lk.imms.management_system.asset.petition.entity.Petition;
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
public class MinutePetition extends AuditEntity {

    @Enumerated( EnumType.STRING )
    private MinuteState minuteState;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Minute minute;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Petition petition;

    @Transient
    private List< MultipartFile > files = new ArrayList<>();

    @Transient
    private List< String > removeImages = new ArrayList<>();

}
