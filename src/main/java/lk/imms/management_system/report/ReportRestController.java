package lk.imms.management_system.report;

import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.court.service.CourtService;
import lk.imms.management_system.asset.crime.service.CrimeService;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.offenders.service.OffenderService;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/report" )
public class ReportRestController {
    private final ContraveneService contraveneService;
    private final CourtService courtService;
    private final CrimeService crimeService;
    private final DetectionTeamService detectionTeamService;
    private final EmployeeService employeeService;
    private final MinutePetitionService minutePetitionService;
    private final OffenderService offenderService;
    private final PetitionerService petitionerService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public ReportRestController(ContraveneService contraveneService, CourtService courtService,
                                CrimeService crimeService, DetectionTeamService detectionTeamService,
                                EmployeeService employeeService, MinutePetitionService minutePetitionService,
                                OffenderService offenderService, PetitionerService petitionerService,
                                UserService userService, RoleService roleService) {
        this.contraveneService = contraveneService;
        this.courtService = courtService;
        this.crimeService = crimeService;
        this.detectionTeamService = detectionTeamService;
        this.employeeService = employeeService;
        this.minutePetitionService = minutePetitionService;
        this.offenderService = offenderService;
        this.petitionerService = petitionerService;
        this.userService = userService;
        this.roleService = roleService;
    }
}
