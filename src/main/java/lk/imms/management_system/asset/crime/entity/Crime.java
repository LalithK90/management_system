package lk.imms.management_system.asset.crime.entity;

import lk.imms.management_system.asset.court.entity.Court;
import lk.imms.management_system.asset.crime.entity.entity.CrimeStatus;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crime extends AuditEntity {

    @Column( unique = true )
    private String crimeNumber;

    private String courtCaseNumber;

    private String compoundedChargeSheetNumber;

    @Column( columnDefinition = "VARCHAR(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String result;

    private String rewardSheetNumber;

    @Column( columnDefinition = "VARCHAR(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String remark;

    @Enumerated( EnumType.STRING )
    private CrimeStatus crimeStatus;

    @Column( columnDefinition = "VARCHAR(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL" )
    private String placeOfOffence;

    @ManyToOne
    private DetectionTeam detectionTeam;

    @ManyToOne
    private Court court;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfDetection;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate compoundedChargeSheetDate;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfOrderOfPersecution;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfFillingPlaint;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfJudgement;

}
