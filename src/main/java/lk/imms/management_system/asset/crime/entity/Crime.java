package lk.imms.management_system.asset.crime.entity;

import lk.imms.management_system.asset.court.entity.Court;
import lk.imms.management_system.asset.crime.entity.entity.CrimeStatus;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crime extends AuditEntity {
    @Column( columnDefinition = "VARCHAR(15000) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 15000 )
    private String result;

    @Column( columnDefinition = "VARCHAR(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 4000 )
    private String remark;

    @Column( columnDefinition = "VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 500 )
    private String placeOfOffence;

    @Column( unique = true, length = 100)
    private String crimeNumber;

    @Column( unique = true, length = 100)
    private String courtCaseNumber;

    @Column( unique = true, length = 100)
    private String compoundedChargeSheetNumber;

    @Column( unique = true, length = 100)
    private String rewardSheetNumber;

    @Enumerated( EnumType.STRING )
    private CrimeStatus crimeStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    private DetectionTeam detectionTeam;

    @ManyToOne( fetch = FetchType.EAGER )
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
