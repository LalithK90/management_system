package lk.imms.management_system.asset.crime.entity;

import lk.imms.management_system.asset.detection.entity.DetectionTeam;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.util.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class Crime extends AuditEntity {

    @Column( unique = true )
    private String crimeNumber;

    private String courtCaseNumber;

    private String compoundedChargeSheetNumber;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate compoundedChargeSheetDate;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfOrderOfPersecution;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfFillingPlaint;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfJudgement;

    @Column( length = 50000 )
    private String result;

    private String rewardSheetNumber;

    private String remark;

    @ManyToOne
    private DetectionTeam detectionTeam;

    @ManyToOne
    private Court court;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfDetection;

    private String placeOfOffence;

    @ManyToMany( cascade = CascadeType.ALL )
    @JoinTable( name = "crime_offender",
            joinColumns = @JoinColumn( name = "crime_id" ),
            inverseJoinColumns = @JoinColumn( name = "offender_id" ) )
    @Fetch( value = FetchMode.SUBSELECT )
    private List< Offender > offenders;
 
}
