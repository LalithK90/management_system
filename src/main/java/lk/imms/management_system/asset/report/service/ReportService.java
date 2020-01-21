package lk.imms.management_system.asset.report.service;

import lk.imms.management_system.asset.OffednerGuardian.entity.Guardian;
import lk.imms.management_system.asset.OffednerGuardian.service.GuardianService;
import lk.imms.management_system.asset.commonAsset.service.CommonService;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.court.service.CourtService;
import lk.imms.management_system.asset.crime.service.CrimeService;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamMemberService;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.service.OffenderService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.asset.petitionAddOffender.service.PetitionOffenderService;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.service.DateTimeAgeService;
import lk.imms.management_system.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService {
    private final ContraveneService contraveneService;
    private final PetitionOffenderService petitionOffenderService;
    private final PetitionService petitionService;
    private final DetectionTeamMemberService detectionTeamMemberService;
    private final GuardianService guardianService;
    private final DateTimeAgeService dateTimeAgeService;
    private final CourtService courtService;
    private final CrimeService crimeService;
    private final DetectionTeamService detectionTeamService;
    private final EmployeeService employeeService;
    private final MinutePetitionService minutePetitionService;
    private final OffenderService offenderService;
    private final PetitionerService petitionerService;
    private final UserService userService;
    private final RoleService roleService;
    private final MinutePetitionFilesService minutePetitionFilesService;
    private final PetitionStateService petitionStateService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final CommonService commonService;

    public ReportService(ContraveneService contraveneService, CourtService courtService, CrimeService crimeService,
                         DetectionTeamService detectionTeamService, DetectionTeamMemberService detectionTeamMemberService, EmployeeService employeeService, MinutePetitionService minutePetitionService, OffenderService offenderService, PetitionerService petitionerService, UserService userService, RoleService roleService, PetitionService petitionService, MinutePetitionFilesService minutePetitionFilesService, PetitionStateService petitionStateService, MakeAutoGenerateNumberService makeAutoGenerateNumberService, CommonService commonService, PetitionOffenderService petitionOffenderService, GuardianService guardianService, DateTimeAgeService dateTimeAgeService) {
        this.contraveneService = contraveneService;
        this.courtService = courtService;
        this.crimeService = crimeService;
        this.detectionTeamService = detectionTeamService;
        this.detectionTeamMemberService = detectionTeamMemberService;
        this.employeeService = employeeService;
        this.minutePetitionService = minutePetitionService;
        this.offenderService = offenderService;
        this.petitionerService = petitionerService;
        this.userService = userService;
        this.roleService = roleService;
        this.petitionService = petitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionStateService = petitionStateService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.commonService = commonService;
        this.petitionOffenderService = petitionOffenderService;
        this.guardianService = guardianService;
        this.dateTimeAgeService = dateTimeAgeService;
    }

//wish to create
/*
1. contravene
2. crime
3. detection
4. guardian
5. petitioner
6. petition
7. user
8. working place.
 */


    //contravene match with offender
    @Transactional
    public Map< String, Long > contraveneWithOffenderAndCreatedBetween(Offender offender, LocalDate from,
                                                                       LocalDate to) {
        Map< String, Long > contraveneWithOffendersMap = new HashMap<>();
        for ( PetitionOffender petitionOffender : petitionOffenderService.countByOffenderAndCreatedAtBetween(offender,
                                                                                                             dateTimeAgeService.dateTimeToLocalDateStartInDay(from), dateTimeAgeService.dateTimeToLocalDateEndInDay(to)) ) {
            for ( Contravene contravene : contraveneService.findAll() ) {

            }
        }


        return contraveneWithOffendersMap;
    }

    //working place match with petition
    @Transactional
    public Map< String, Long > petitionWithWorkingPlaceAndCreatedBetween(List< WorkingPlace > workingPlaces,
                                                                         LocalDate from,
                                                                         LocalDate to) {
        Map< String, Long > petitionWithWorkingPlacesMap = new HashMap<>();
        for ( WorkingPlace workingPlace : workingPlaces ) {
            petitionWithWorkingPlacesMap.put(workingPlace.getName(),
                                             petitionService.countByWorkingPlaceAndCreatedAtBetween(workingPlace,
                                                                                                    dateTimeAgeService.dateTimeToLocalDateStartInDay(from), dateTimeAgeService.dateTimeToLocalDateEndInDay(to))
                                            );
        }
        return petitionWithWorkingPlacesMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                          LinkedHashMap::new));
    }

    //employee detection team
    @Transactional
    public Map< String, Long > employeeWithDetectionTeamAndCreatedBetween(List< Employee > employees, LocalDate from,
                                                                          LocalDate to) {
        Map< String, Long > employeeWithDetectionTeamMap = new HashMap<>();
        for ( Employee employee : employees ) {
            employeeWithDetectionTeamMap.put(employee.getName(),
                                             (long) detectionTeamMemberService.findByEmployeeAndCreatedAtBetween(employee,
                                                                                                                 dateTimeAgeService.dateTimeToLocalDateStartInDay(from), dateTimeAgeService.dateTimeToLocalDateEndInDay(to)).size());
        }
        return employeeWithDetectionTeamMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

    }

    //guardian offender
    @Transactional
    public Map< String, List< Offender > > guardianWithOffenderAndCreatedBetween(LocalDate from,
                                                                                 LocalDate to) {
        Map< String, List< Offender > > guardianWithOffenderMap = new HashMap<>();
        for ( Guardian guardian :
                guardianService.findByGuardianAndCreateBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(from)
                        , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)) ) {
            String guardianNameAndNic = guardian.getName() + " - " + guardian.getNic();
            guardianWithOffenderMap.put(guardianNameAndNic, guardian.getOffenders());
        }
        return guardianWithOffenderMap;

    }
}
