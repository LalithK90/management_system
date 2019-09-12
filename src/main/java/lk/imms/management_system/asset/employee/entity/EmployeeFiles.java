package lk.imms.management_system.asset.employee.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeFiles {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String fileName;
    private String modifiedFileName;
    private String fileExtension;

    @ManyToOne
    private Employee employee;

    public EmployeeFiles(String fileName, String modifiedFileName, String fileExtension) {
        this.fileName = fileName;
        this.modifiedFileName = modifiedFileName;
        this.fileExtension = fileExtension;
    }
}
