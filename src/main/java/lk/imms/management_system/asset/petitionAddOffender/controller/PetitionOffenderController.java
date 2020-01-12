package lk.imms.management_system.asset.petitionAddOffender.controller;

import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.offender.controller.OffenderRestController;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.service.OffenderService;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.asset.petitionAddOffender.service.PetitionOffenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/petitionOffender" )
public class PetitionOffenderController {
    private final PetitionOffenderService petitionOffenderService;
    private final PetitionService petitionService;
    private final ContraveneService contraveneService;
    private final OffenderService offenderService;

    @Autowired
    public PetitionOffenderController(PetitionOffenderService petitionOffenderService,
                                      PetitionService petitionService, ContraveneService contraveneService,
                                      OffenderService offenderService) {
        this.petitionOffenderService = petitionOffenderService;
        this.petitionService = petitionService;
        this.contraveneService = contraveneService;
        this.offenderService = offenderService;
    }

    //a common thing to add an offender to petition
    private String commonOffenderSetPetition(Model model, Petition petition) {
        model.addAttribute("petitionNum", petition.getPetitionNumber());
        model.addAttribute("offenderUrl", MvcUriComponentsBuilder
                .fromMethodName(OffenderRestController.class, "getOffender", "")
                .build()
                .toString());
        return "petitionOffender/petitionAddOffender";
    }

    //Give a frontend to petition offender from
    @GetMapping( "/add/{id}" )
    public String addPetitionOffenderPage(Model model, @PathVariable Long id) {
        PetitionOffender petitionOffender = new PetitionOffender();
        //set petition
        Petition petition = petitionService.findById(id);
        petitionOffender.setPetition(petition);
        List< Offender > offenders = new ArrayList<>();
        petitionOffenderService.findByPetition(petition).forEach(x -> offenders.add(x.getOffender()));
        //set offenders to the petition offender to list front end
        petitionOffender.setOffenders(offenders);
        model.addAttribute("petitionOffender", petitionOffender);
        return commonOffenderSetPetition(model, petitionOffender.getPetition());
    }

    //offender add to particular petition
    @PostMapping( value = {"/add", "/update"} )
    public String saveOffenderForPetition(@ModelAttribute( "petitionOffender" ) PetitionOffender petitionOffender,
                                          Model model, BindingResult result) {
        if ( result.hasErrors() && petitionOffender.getOffenders().isEmpty() ) {
            model.addAttribute("petition", petitionOffender.getPetition());
            ObjectError objectError = new ObjectError("petition", "You forgot to select offender for this.\n Please " +
                    "select Offenders and re submit");
            result.addError(objectError);
            model.addAttribute("offenders", petitionOffender.getOffenders());
            model.addAttribute("petitionOffender", petitionOffender);
            return commonOffenderSetPetition(model, petitionOffender.getPetition());
        }
        //petition relevant
        Petition petition = petitionService.findById(petitionOffender.getPetition().getId());
        //offender petition according to the petition
        List< PetitionOffender > petitionOffenders =
                petitionOffenderService.findByPetition(petition);

        //take a count according to petition how may offenders are there and how many offender comes from front-end size
//compare those value and delete unnecessary ones
        if ( petitionOffenders.size() > petitionOffender.getOffenders().size() ) {
            //petition offenders come from still in db
            List< PetitionOffender > petitionOffenderList = new ArrayList<>();
            for ( Offender offender : petitionOffender.getOffenders() ) {
                //find petition offender from a database and set it still remaining array
                petitionOffenderList.add(petitionOffenderService.findByPetitionAndOffender(petition, offender));
            }
            //add all petition offenders to one array
            petitionOffenders.addAll(petitionOffenderList);
            //remove duplicate from all array when remove duplicate need to delete other's to remain required
            // offenders
            petitionOffenderService.deleteAll(petitionOffenders.stream().distinct().collect(Collectors.toList()));
        }
        //last petitionOffender id
        Long newId;
        PetitionOffender petitionOffenderLast = petitionOffenderService.getLast();
        if ( petitionOffenderLast == null ) {
            newId = Long.valueOf("1");
        } else {
            newId = petitionOffenderLast.getId() + 1;
        }

        PetitionOffender petitionOffenderMake = new PetitionOffender();
        for ( Offender offender : petitionOffender.getOffenders() ) {
            petitionOffenderMake.setId(newId);
            petitionOffenderMake.setOffender(offenderService.findById(offender.getId()));
            petitionOffenderMake.setPetition(petition);
            if ( petitionOffenderService.findByPetitionAndOffender(petition, offender) == null ) {
                petitionOffenderService.persist(petitionOffenderMake);
                newId++;
            }
        }
        return "redirect:/petitionOffender/contraveneAdd/" + petitionOffender.getPetition().getId();
    }

    //add contravene to petition added offender
    @GetMapping( "/contraveneAdd/{id}" )
    public String addOffenderContravenePage(@PathVariable Long id, Model model) {
        model.addAttribute("petitionOffenders",
                           petitionOffenderService.findByPetition(petitionService.findById(id)));
        model.addAttribute("contravenes", contraveneService.findAll());
        model.addAttribute("petition", new Petition());
        return "offenderContravene/addOffenderContravene";
    }

    //save contravene to offender
    @PostMapping( "/contraveneAdd" )
    public String saveContraveneForOffender(@ModelAttribute( "petition" ) Petition petition,
                                            BindingResult result, Model model) {

        System.out.println("petition offender "+petition.toString());
        petition.getPetitionOffenders().forEach(System.out::println);
        boolean notContravene = false;
        Petition beforePersistPetition = new Petition();

        List<PetitionOffender> petitionOffenders = new ArrayList<>();

        PetitionOffender beforePersistPetitionOffender ;

        for ( PetitionOffender petitionOffender : petition.getPetitionOffenders()) {
            notContravene = petitionOffender.getContravenes().isEmpty();
            petition = petitionOffender.getPetition();
            beforePersistPetitionOffender = petitionOffenderService.findByPetitionAndOffender(petitionOffender.getPetition(), petitionOffender.getOffender());
            beforePersistPetitionOffender.setContravenes(petitionOffender.getContravenes());
            petitionOffenders.add(beforePersistPetitionOffender);

        }

        if ( result.hasErrors() && notContravene ) {
            ObjectError objectError = new ObjectError("petitionOffender", "You forgot to select contravene for this, " +
                    "or those offenders or offender.\n Please " +
                    "select Contravene and re submit");
            result.addError(objectError);
            model.addAttribute("petitionOffenders", petition.getPetitionOffenders());
            model.addAttribute("contravenes", contraveneService.findAll());
            return "redirect:/petitionOffender/contraveneAdd/" + beforePersistPetition.getId();
        }
        petitionOffenders.forEach(petitionOffenderService::persist);
        return "redirect:/detection";
    }
}
