package lk.imms.management_system.asset.detection.controller;

import lk.imms.management_system.asset.detection.entity.DetectionTeam;
import lk.imms.management_system.asset.detection.service.DetectionTeamMemberService;
import lk.imms.management_system.asset.detection.service.DetectionTeamService;
import lk.imms.management_system.asset.minute.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping( "/detection" )
public class DetectionController {
    private final DetectionTeamService detectionTeamService;
    private final DetectionTeamMemberService detectionTeamMemberService;
    private final MinutePetitionService minutePetitionService;
    private final PetitionService petitionService;

    @Autowired
    public DetectionController(DetectionTeamService detectionTeamService,
                               DetectionTeamMemberService detectionTeamMemberService,
                               MinutePetitionService minutePetitionService, PetitionService petitionService) {
        this.detectionTeamService = detectionTeamService;
        this.detectionTeamMemberService = detectionTeamMemberService;
        this.minutePetitionService = minutePetitionService;
        this.petitionService = petitionService;
    }


    @GetMapping
    public String allTeams(Model model) {
        //todo need to send login-user relevant petition to frontend
        model.addAttribute("detectionTeam", detectionTeamMemberService.findAll());
        return "detectionTeam/detectionTeam";
    }

    //@GetMapping( "/create/{id}" )
    public String createTeam(Model model, @PathVariable Long id) {
        DetectionTeam detectionTeam = new DetectionTeam();
        detectionTeam.setPetition(petitionService.findById(id));
        model.addAttribute("detectionTeam", detectionTeam);
        return "detectionTeam/addDetectionTeam";
    }

  //  @PostMapping( "/add" )
    public String persistTeam(@Valid @ModelAttribute DetectionTeam detectionTeam, Model model, BindingResult result) {
       if(result.hasErrors()){
           model.addAttribute("detectionTeam", detectionTeam);

           //todo -> while on saving all team member should be informed using email
           return "detectionTeam/addDetectionTeam";
       }
        return "redirect:/petition";
    }

//update team status by team leader


    //add minute with an arrested offender also need to add
    public String addMinute() {

        return "redirect:/detection";
    }


}
