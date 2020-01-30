package lk.imms.management_system.asset.report.controller;

import lk.imms.management_system.asset.commonAsset.model.NameCount;
import lk.imms.management_system.asset.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping( "/report" )
public class ReportRestController {
    private final ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/contra")
    public List< NameCount > findContravene() {
        return reportService.listContraveneWorkingPlace(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/crimeNo")
    public List< NameCount > findCrimeNo() {
        return reportService.listCrimeWorkingPlaceCrimeStatusNo(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/crimePart")
    public List< NameCount > findCrimePartially() {
        return reportService.listCrimeWorkingPlaceCrimeStatusPartially(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/crimeCom")
    public List< NameCount > findCrimeComp() {
        return reportService.listCrimeWorkingPlaceCrimeStatusComplete(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/detectionSucc")
    public List< NameCount > findDetectionSucc() {
        return reportService.detectionTeamStatusSuccess(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/detectionSuccNot")
    public List< NameCount > findDetectionSuccNot() {
        return reportService.detectionTeamStatusNotSuccess(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/guardianOffender")
    public List< NameCount > findGuardianOffender() {
        return reportService.guardianOffender(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/workingPlacePetitionCount")
    public List< NameCount > findWorkingPlacePetitionCount() {
        return reportService.workingPlacePetitionCount(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/workingPlacePetitionPriority")
    public List< NameCount > findWorkingPlacePetitionPriority() {
        return reportService.workingPlacePetitionPriority(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/workingPlacePetitionType")
    public List< NameCount > findWorkingPlacePetitionType() {
        return reportService.workingPlacePetitionType(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/workingPlacePetitionStateType")
    public List< NameCount > findWorkingPlacePetitionStateType() {
        return reportService.workingPlacePetitionStateType(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/contraveneCountAll")
    public List< NameCount > findContraveneCountAll() {
        return reportService.contraveneCountAll(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @GetMapping("/employeeDetectionTeam")
    public List< NameCount > findEmployeeDetectionTeam() {
        return reportService.employeeDetectionTeam(LocalDate.now().minusMonths(1), LocalDate.now());
    }
}
