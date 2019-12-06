package lk.imms.management_system.asset.petition.controller;

import lk.imms.management_system.asset.petition.entity.Petitioner;
import lk.imms.management_system.asset.petition.service.PetitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping( "/petitioner" )
public class PetitionerController {
    private final PetitionerService petitionerService;

    @Autowired
    public PetitionerController(PetitionerService petitionerService) {
        this.petitionerService = petitionerService;
    }

    //All petitioner list send to frontend
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("petitioners", petitionerService.findAll());
        return "petitioner/petitioner";
    }

    //Given petitioner add details from
    @GetMapping( "/add" )
    public String addForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("petitioner", new Petitioner());
        return "petitioner/addPetitioner";
    }

    //Send on petitioner details
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String petitionerView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("petitioner", petitionerService.findById(id));
        return "petitioner/petitioner-detail";
    }


    //Send employee data edit
    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET )
    public String editPetitionerFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("petitioner", petitionerService.findById(id));
        return "petitioner/addPetitioner";
    }

    //This method is used petitioner details save and update
    @PostMapping( value = {"/add", "/update"} )
    public String savePetitioner(@Valid @ModelAttribute( "petitioner" ) Petitioner petitioner,
                                 BindingResult result, Model model) {

        if ( petitioner.getNameEnglish().isEmpty() || petitioner.getNameSinhala().isEmpty() || petitioner.getNameTamil().isEmpty() ) {
            ObjectError error = new ObjectError("petitionerDetail", "There are no possibilities to empty petitioner " +
                    "name, it should be included any language, which is you used ");
            result.addError(error);
        }
        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            model.addAttribute("petitionerDetail", petitionerService);
            return "petitioner/addPetitioner";
        }
        petitionerService.persist(petitioner);
        return "redirect:/petitioner";
    }
//Delete is not applicable for this project

    //to search employee any giving employee parameter
    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public String search(Model model, Petitioner petitioner) {
        model.addAttribute("petitioners", petitionerService.search(petitioner));
        return "petitioner/petitioner";
    }
}
