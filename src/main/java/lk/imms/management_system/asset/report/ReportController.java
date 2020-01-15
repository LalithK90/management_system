package lk.imms.management_system.asset.report;

import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.court.service.CourtService;
import lk.imms.management_system.asset.crime.service.CrimeService;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.offender.service.OffenderService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import lk.imms.management_system.asset.petitionAddOffender.service.PetitionOffenderService;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.util.service.MakeAutoGenerateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/report" )
public class ReportController {
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
    private final PetitionService petitionService;
    private final MinutePetitionFilesService minutePetitionFilesService;
    private final PetitionStateService petitionStateService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final CommonCodeService commonCodeService;
    private final PetitionOffenderService petitionOffenderService;

    @Autowired
    public ReportController(ContraveneService contraveneService, CourtService courtService, CrimeService crimeService
            , DetectionTeamService detectionTeamService, EmployeeService employeeService,
                            MinutePetitionService minutePetitionService, OffenderService offenderService,
                            PetitionerService petitionerService, UserService userService, RoleService roleService,
                            PetitionService petitionService, MinutePetitionFilesService minutePetitionFilesService,
                            PetitionStateService petitionStateService, MakeAutoGenerateNumberService makeAutoGenerateNumberService, CommonCodeService commonCodeService, PetitionOffenderService petitionOffenderService) {
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
        this.petitionService = petitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionStateService = petitionStateService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.commonCodeService = commonCodeService;
        this.petitionOffenderService = petitionOffenderService;
    }


}
