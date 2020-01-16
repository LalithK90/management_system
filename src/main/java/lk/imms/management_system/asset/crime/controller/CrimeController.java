package lk.imms.management_system.asset.crime.controller;

import lk.imms.management_system.asset.court.service.CourtService;
import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.crime.service.CrimeService;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CrimeController(CrimeService crimeService, CourtService courtService,
                           DetectionTeamService detectionTeamService, PetitionService petitionService) {
        this.crimeService = crimeService;
        this.courtService = courtService;
        this.detectionTeamService = detectionTeamService;
        this.petitionService = petitionService;
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
        if ( result.hasErrors() ) {
            return commonCode(model, crime, true);
        }

        crimeService.persist(crime);
        return "redirect:/detection";
    }

}
