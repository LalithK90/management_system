package lk.imms.management_system.asset.petitioner.controller;

import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionerType;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
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
    private final CommonCodeService commonCodeService;

    @Autowired
    public PetitionerController(PetitionerService petitionerService, CommonCodeService commonCodeService) {
        this.petitionerService = petitionerService;
        this.commonCodeService = commonCodeService;
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
        model.addAttribute("petitionerTypes", PetitionerType.values());
        model.addAttribute("petitioner", new Petitioner());
        return "petitioner/addPetitioner";
    }

    //Send on petitioner details
    @GetMapping( value = "/{id}" )
    public String petitionerView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("petitionerDetail", petitionerService.findById(id));
        return "petitioner/petitioner-detail";
    }
    //Send employee data edit
    @GetMapping( value = "/edit/{id}")
    public String editPetitionerFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("petitionerTypes", PetitionerType.values());
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
            model.addAttribute("petitionerTypes", PetitionerType.values());
            model.addAttribute("petitioner", petitioner);
            return "petitioner/addPetitioner";
        }
        petitioner.setMobileOne(commonCodeService.commonMobileNumberLengthValidator(petitioner.getMobileOne()));
        petitioner.setMobileTwo(commonCodeService.commonMobileNumberLengthValidator(petitioner.getMobileTwo()));
        petitioner.setLand(commonCodeService.commonMobileNumberLengthValidator(petitioner.getLand()));

        petitionerService.persist(petitioner);
        return "redirect:/petitioner";
    }
//Delete is not applicable for this project

    //to search employee any giving employee parameter
    @GetMapping( value = "/search" )
    public String search(Model model, Petitioner petitioner) {
        model.addAttribute("petitioners", petitionerService.search(petitioner));
        return "petitioner/petitioner";
    }
}
