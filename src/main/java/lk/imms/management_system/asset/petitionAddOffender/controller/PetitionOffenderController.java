package lk.imms.management_system.asset.petitionAddOffender.controller;

import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.offender.controller.OffenderRestController;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petitionAddOffender.service.PetitionOffenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping( "/petitionOffender" )
public class PetitionOffenderController {
    private final PetitionOffenderService petitionOffenderService;
    private final PetitionService petitionService;
    private final ContraveneService contraveneService;

    @Autowired
    public PetitionOffenderController(PetitionOffenderService petitionOffenderService, PetitionService petitionService, ContraveneService contraveneService) {
        this.petitionOffenderService = petitionOffenderService;
        this.petitionService = petitionService;
        this.contraveneService = contraveneService;
    }

    //a common thing to add an offender to petition
    private String commonOffenderSetPetition(Model model, Petition petition){
        model.addAttribute("petition", petition);
        model.addAttribute("petitionNum", petition.getPetitionNumber());
        model.addAttribute("contravenes", contraveneService.findAll());
        model.addAttribute("offenderUrl", MvcUriComponentsBuilder
                .fromMethodName(OffenderRestController.class, "getOffender", "")
                .build()
                .toString());
        return "petitionOffender/petitionAddOffender";
    }

    //Give a frontend to petition offender from
    @GetMapping( "/add/{id}" )
    public String addPetitionOffenderPage(Model model, @PathVariable Long id) {
        Petition petition = petitionService.findById(id);
        return commonOffenderSetPetition(model, petition);
    }
    //offender add to particular petition
    @PostMapping(value = {"/add","/update"})
    public String saveOffenderForPetition(@ModelAttribute("petition") Petition petition, Model model, BindingResult result, RedirectAttributes redirectAttributes){
        if ( result.hasErrors() && petition.getPetitionOffenders().isEmpty()) {
            model.addAttribute("petition", petition);
            ObjectError objectError = new ObjectError("petition", "You forgot to select offender for this.\n Please select Offenders and re submit");
            result.addError(objectError);
            return commonOffenderSetPetition(model, petition);
        }
        if (petition.getId() != null){

        }
        petition.getPetitionOffenders().forEach(System.out::println);
        System.out.println("petition id "+ petition.getId());
        //Petition persistPetition =  petitionService.persist(petition);

        //redirectAttributes.addFlashAttribute("id",petition.getId() );
        return "redirect:/petitionOffender/ContraveneAdd/"+petition.getId();
    }
    //add contravene to petition added offender
    @GetMapping( "/ContraveneAdd/{id}" )
    public String addOffenderContravenePage(Model model, @PathVariable Long id) {
        System.out.println("id comming "+ id);
        return "";
    }
}
