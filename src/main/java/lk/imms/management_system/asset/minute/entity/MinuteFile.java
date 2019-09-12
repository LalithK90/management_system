package lk.imms.management_system.asset.minute.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinuteFile{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;
    private String mimeType;

    @Lob
    @Column( name = "pic" )
    private byte[] pic;

    @ManyToOne
    private Minute minute;

    public MinuteFile(String name, String mimeType, byte[] pic) {
        this.name = name;
        this.mimeType = mimeType;
        this.pic = pic;
    }
}