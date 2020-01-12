package lk.imms.management_system.asset.detectionTeam.controller;

import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeamMember;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeamNote;
import lk.imms.management_system.asset.detectionTeam.entity.Enum.DetectionTeamMemberRole;
import lk.imms.management_system.asset.detectionTeam.entity.Enum.DetectionTeamStatus;
import lk.imms.management_system.asset.detectionTeam.entity.Enum.TeamAcceptation;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamMemberService;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.util.service.DateTimeAgeService;
import lk.imms.management_system.util.service.MakeAutoGenerateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/detection" )
public class DetectionController {
    private final DetectionTeamService detectionTeamService;
    private final DetectionTeamMemberService detectionTeamMemberService;
    private final MinutePetitionService minutePetitionService;
    private final PetitionService petitionService;
    private final UserService userService;
    private final CommonCodeService commonCodeService;
    private final DateTimeAgeService dateTimeAgeService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

    @Autowired
    public DetectionController(DetectionTeamService detectionTeamService,
                               DetectionTeamMemberService detectionTeamMemberService,
                               MinutePetitionService minutePetitionService, PetitionService petitionService,
                               UserService userService, CommonCodeService commonCodeService,
                               DateTimeAgeService dateTimeAgeService,
                               MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
        this.detectionTeamService = detectionTeamService;
        this.detectionTeamMemberService = detectionTeamMemberService;
        this.minutePetitionService = minutePetitionService;
        this.petitionService = petitionService;
        this.userService = userService;
        this.commonCodeService = commonCodeService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    }


    @GetMapping
    public String allTeams(Model model) {
        //get current login user
        User currentUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("detectionTeams",
                           detectionTeamService.findAll());
        /*model.addAttribute("detectionTeams",
                           detectionTeamMemberService.findAll()
                                   .stream()
                                   .filter((x) -> x.getEmployee().equals(currentUser.getEmployee()))
                                   .collect(Collectors.toList()));*/
        return "detectionTeam/detectionTeam";
    }

    private void commonCode(Model model, DetectionTeam detectionTeam) {
        //url to find employee
        commonCodeService.commonUrlBuilder(model);
        model.addAttribute("teamMemberRoles", DetectionTeamMemberRole.values());
        model.addAttribute("teamAcceptations", TeamAcceptation.values());
        model.addAttribute("detectionTeamStatuses", DetectionTeamStatus.values());
        model.addAttribute("detectionTeam", detectionTeam);
    }


    @GetMapping( "/create/{id}" )
    public String createTeam(Model model, @PathVariable Long id) {
        DetectionTeam detectionTeam = new DetectionTeam();
        detectionTeam.setPetition(petitionService.findById(id));
//common code for detection team
        commonCode(model, detectionTeam);
        return "detectionTeam/addDetectionTeam";
    }

    @GetMapping( "/edit/{id}" )
    public String editTeam(Model model, @PathVariable Long id) {
        DetectionTeam detectionTeam = detectionTeamService.findById(id);
        //get detection team and check whether employee is in or not in detection
        // team members array and return only employee in team members
        detectionTeam.setDetectionTeamMembers(
                detectionTeam.getDetectionTeamMembers()
                        .stream()
                        .filter(x -> x.getEmployee() != null)
                        .collect(Collectors.toList()));
        //common code for detection team
        commonCode(model, detectionTeam);
        model.addAttribute("detectionTeamNotes", detectionTeam.getDetectionTeamNotes());
        model.addAttribute("addStatus", false);
        return "detectionTeam/addDetectionTeam";
    }


    @PostMapping( value = {"/add", "/update"} )
    public String persistTeam(@Valid @ModelAttribute DetectionTeam detectionTeam, Model model, BindingResult result) {
//get current login user
        User currentUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        if ( result.hasErrors() ) {
            commonCode(model, detectionTeam);
            return "detectionTeam/addDetectionTeam";
        }
        boolean inChargeInOrExit = false;

        List< DetectionTeamMember > detectionTeamMemberList = new ArrayList<>();
//if there are no members in detection team automatically delete
        if ( detectionTeam.getDetectionTeamMembers() != null ) {
            for ( DetectionTeamMember teamMember : detectionTeam.getDetectionTeamMembers() ) {
                teamMember.setDetectionTeam(detectionTeam);
                detectionTeamMemberList.add(teamMember);
            }
//if there is no team without in-charge in team
            for ( DetectionTeamMember teamMember : detectionTeam.getDetectionTeamMembers() ) {
                if ( teamMember.getDetectionTeamMemberRole().equals(DetectionTeamMemberRole.INCHARGE) ) {
                    inChargeInOrExit = true;
                    break;
                }
            }
            if (!inChargeInOrExit ){
                commonCode(model, detectionTeam);
                return "detectionTeam/addDetectionTeam";
            }
        } else if ( !detectionTeam.getDetectionTeamStatus().equals(DetectionTeamStatus.SUCCESS) ) {
            //successfully completed team cannot be deleted
            detectionTeamService.delete(detectionTeam.getId());
            return "redirect:/petition";
        }

// detection team is not null need to add detection team to it
        if ( detectionTeam.getDetectionTeamNotes() != null ) {
            List< DetectionTeamNote > detectionTeamNotes = new ArrayList<>();
            for ( DetectionTeamNote detectionTeamNote : detectionTeam.getDetectionTeamNotes() ) {
                detectionTeamNote.setDetectionTeam(detectionTeam);
                detectionTeamNotes.add(detectionTeamNote);
            }
            detectionTeam.setDetectionTeamNotes(detectionTeamNotes);
        }

//detection team acceptation, when creating detection some
        if ( detectionTeam.getId() == null ) {
            detectionTeam.setTeamAcceptation(TeamAcceptation.NO);
            detectionTeam.setDetectionTeamStatus(DetectionTeamStatus.NOTSUCCESS);
        } else if ( detectionTeam.getTeamAcceptation().equals(TeamAcceptation.YES) ) {
            detectionTeam.setTeamAcceptedDateTime(dateTimeAgeService.getCurrentDateTime());
        }

        if ( detectionTeam.getId() == null ) {
            if ( detectionTeamService.getLastTeam() == null ) {
                detectionTeam.setNumber(makeAutoGenerateNumberService.numberAutoGen(null).toString());
            } else {
                String number =
                        makeAutoGenerateNumberService.numberAutoGen(detectionTeamService.getLastTeam().getNumber()).toString();
                detectionTeam.setNumber(number);
            }

        }

        detectionTeam.setDetectionTeamMembers(detectionTeamMemberList);
        DetectionTeam persistDetectionTeam = detectionTeamService.persist(detectionTeam);
//if detection team status success need to add an offender for relevant petition hence

        boolean teamIn_ChargeOrNot = false;
        for ( DetectionTeamMember teamMember : persistDetectionTeam.getDetectionTeamMembers() ) {
            if ( teamMember.getEmployee().equals(currentUser.getEmployee()) && teamMember.getDetectionTeamMemberRole().equals(DetectionTeamMemberRole.INCHARGE) ) {
                teamIn_ChargeOrNot = true;
                break;
            }
            teamIn_ChargeOrNot = false;
        }

        if ( teamIn_ChargeOrNot ) {
            return "redirect:/petition";
        } else {
            return "redirect:/detection";
        }
//todo -> while on saving all team member should be informed using email

    }

    @GetMapping( "/remove/{id}" )
    public String removeDetectionTeam(@PathVariable Long id) {
        detectionTeamService.delete(id);
        return "redirect:/detection";
    }


}
