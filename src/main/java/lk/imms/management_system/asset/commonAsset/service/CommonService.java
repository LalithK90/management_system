package lk.imms.management_system.asset.commonAsset.service;

import lk.imms.management_system.asset.OffednerGuardian.entity.Guardian;
import lk.imms.management_system.asset.OffednerGuardian.service.GuardianService;
import lk.imms.management_system.asset.commonAsset.entity.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Title;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamMemberService;
import lk.imms.management_system.asset.employee.controller.EmployeeRestController;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.asset.petitionAddOffender.service.PetitionOffenderService;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommonService {
    private final ContraveneService contraveneService;
    private final PetitionOffenderService petitionOffenderService;
    private final PetitionService petitionService;
    private final DetectionTeamMemberService detectionTeamMemberService;
    private final GuardianService guardianService;
    private final DateTimeAgeService dateTimeAgeService;

    @Autowired
    public CommonService(ContraveneService contraveneService, PetitionOffenderService petitionOffenderService,
                         PetitionService petitionService, DetectionTeamMemberService detectionTeamMemberService,
                         GuardianService guardianService, DateTimeAgeService dateTimeAgeService) {
        this.contraveneService = contraveneService;
        this.petitionOffenderService = petitionOffenderService;
        this.petitionService = petitionService;
        this.detectionTeamMemberService = detectionTeamMemberService;
        this.guardianService = guardianService;
        this.dateTimeAgeService = dateTimeAgeService;
    }

    //common things to employee and offender - start
    public void commonUrlBuilder(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("designations", Designation.values());
        model.addAttribute("provinces", Province.values());
        model.addAttribute("districtUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                .build()
                .toString());
        model.addAttribute("stationUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                .build()
                .toString());
        Object[] arg = {"designation", "id"};
        model.addAttribute("employeeUrl", MvcUriComponentsBuilder
                .fromMethodName(EmployeeRestController.class, "getEmployeeByWorkingPlace", arg)
                .build()
                .toString());
    }

    public void commonEmployeeAndOffender(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
    }

    //common things to employee and offender - end
    //common mobile number length employee,offender,guardian, petitioner - start
    // private final mobile length validator
    public String commonMobileNumberLengthValidator(String number) {
        if ( number.length() == 9 ) {
            number = "0".concat(number);
        }
        return number;
    }

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
    //

}
