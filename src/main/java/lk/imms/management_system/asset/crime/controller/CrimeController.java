package lk.imms.management_system.asset.crime.controller;

import lk.imms.management_system.asset.court.service.CourtService;
import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.crime.entity.entity.CrimeStatus;
import lk.imms.management_system.asset.crime.service.CrimeService;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.userManagement.entity.Role;
import lk.imms.management_system.asset.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "/crime" )
public class CrimeController {
    private final CrimeService crimeService;
    private final CourtService courtService;
    private final DetectionTeamService detectionTeamService;
    private final PetitionService petitionService;
    private final UserService userService;

    @Autowired
    public CrimeController(CrimeService crimeService, CourtService courtService,
                           DetectionTeamService detectionTeamService, PetitionService petitionService,
                           UserService userService) {
        this.crimeService = crimeService;
        this.courtService = courtService;
        this.detectionTeamService = detectionTeamService;
        this.petitionService = petitionService;
        this.userService = userService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("crimes", crimeService.findAll());
        return "crime/crime";

    }

    //crime add edit form common
    private String commonCode(Model model, Crime crime, Boolean state) {
        model.addAttribute("addStatus", state);
        model.addAttribute("courts", courtService.findAll());
        model.addAttribute("crime", crime);
        model.addAttribute("crimeStatuses", CrimeStatus.values());
        return "crime/addCrime";
    }

    @GetMapping( "/view/{id}" )
    public String viewCrime(@PathVariable Long id, Model model) {
        Crime crime = crimeService.findById(id);
        DetectionTeam detectionTeam = detectionTeamService.findById(crime.getDetectionTeam().getId());
        crime.setDetectionTeam(detectionTeam);
        model.addAttribute("petitionNumber",
                           petitionService.findById(detectionTeam.getPetition().getId()).getPetitionNumber());
        model.addAttribute("crime", crime);
        model.addAttribute("crimeStatus", CrimeStatus.values());
        return "crime/crime-detail";
    }

    @GetMapping( "/{id}" )
    public String addForm(Model model, @PathVariable Long id) {
        Crime crime = new Crime();
        crime.setDetectionTeam(detectionTeamService.findById(id));
        return commonCode(model, crime, true);
    }

    @GetMapping( "/edit/{id}" )
    public String editCrime(Model model, @PathVariable Long id) {
        return commonCode(model, crimeService.findById(id), false);
    }

    @PostMapping( value = {"/add", "/update"} )
    public String saveCrime(@ModelAttribute Crime crime, BindingResult result, Model model) {

        boolean authorityRole = false;
        for ( Role role :
                userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName())).getRoles() ) {
            if ( role.getRoleName().equals("CGE") || role.getRoleName().equals("ACGE") || role.getRoleName().equals(
                    "CE") || role.getRoleName().equals("DCL") || role.getRoleName().equals("DCLE") || role.getRoleName().equals("ACE") || role.getRoleName().equals("SE") || role.getRoleName().equals("OIC") ) {
                authorityRole = true;
                break;
            }
        }

        if ( result.hasErrors() ) {
            return commonCode(model, crime, true);
        }
        if ( authorityRole ) {
            if ( crime.getCrimeStatus() == null && crime.getDetectionTeam() != null ) {
                crime.setCrimeStatus(CrimeStatus.NO);
                if ( crime.getCompoundedChargeSheetDate() != null && crime.getDateOfOrderOfPersecution() != null && crime.getCrimeStatus().equals(CrimeStatus.NO) ) {
                    crime.setCrimeStatus(CrimeStatus.PARTIAL);
                    if ( crime.getDateOfFillingPlaint() != null && crime.getDateOfJudgement() != null && crime.getCrimeStatus().equals(CrimeStatus.PARTIAL) ) {
                        crime.setCrimeStatus(CrimeStatus.COMPLETED);
                    }
                }
            }
        }

        crimeService.persist(crime);
        return "redirect:/detection";
    }

}
