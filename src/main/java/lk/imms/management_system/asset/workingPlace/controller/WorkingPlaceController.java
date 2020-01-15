package lk.imms.management_system.asset.workingPlace.controller;

import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.workingPlace.entity.Enum.District;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.Enum.WorkingPlaceType;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/workingPlace" )
public class WorkingPlaceController {

    private final WorkingPlaceService workingPlaceService;
    private final CommonCodeService commonCodeService;

    @Autowired
    public WorkingPlaceController(WorkingPlaceService workingPlaceService, CommonCodeService commonCodeService) {
        this.workingPlaceService = workingPlaceService;
        this.commonCodeService = commonCodeService;
    }

    // common font-end attribute for workingPlace
    private void CommonThings(Model model) {
        model.addAttribute("province", Province.values());
        model.addAttribute("district", District.values());
        model.addAttribute("workingPlaceType", WorkingPlaceType.values());
    }

    /*
     * Send all working place to font end
     * */
    @RequestMapping
    public String workingPlacePage(Model model) {
        model.addAttribute("workingPlaces", workingPlaceService.findAll());
        return "workingPlace/workingPlace";
    }

    /*
     * Send one-{find using id} working place details to font-end view
     * */
    @GetMapping( value = "/{id}" )
    public String workingPlaceView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("workingPlace", workingPlaceService.findById(id));
        model.addAttribute("addStatus", false);
        return "workingPlace/workingPlace-detail";
    }

    /*
     * Send one-{find using id} working place to font-end to edit
     * */
    @GetMapping( value = "/edit/{id}" )
    public String editWorkingPlaceFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("workingPlace", workingPlaceService.findById(id));
        model.addAttribute("addStatus", false);
        CommonThings(model);
        return "workingPlace/addWorkingPlace";
    }

    /*
     * Send form view working place to font-end to add new working place
     * */
    @GetMapping( value = {"/add"} )
    public String workingPlaceAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("workingPlace", new WorkingPlace());
        CommonThings(model);
        return "workingPlace/addWorkingPlace";
    }
    /*
     * New Working palace add and stored working place edit using following method
     * */

    @PostMapping( value = {"/add", "/update"} )
    public String addWorkingPlace(@Valid @ModelAttribute WorkingPlace workingPlace, BindingResult result, Model model
          ) {
        System.out.println("working place " + workingPlace.toString());
        if ( result.hasErrors() ) {
            ObjectError error = new ObjectError("workingPlace", "This working place is already added to the system. ");
            result.addError(error);
            model.addAttribute("addStatus", true);
            model.addAttribute("workingPlace", workingPlace);
            CommonThings(model);
            return "workingPlace/addWorkingPlace";
        }
        workingPlace.setCode(workingPlace.getCode().toUpperCase());
        workingPlace.setLandOne(commonCodeService.commonMobileNumberLengthValidator(workingPlace.getEmailOne()));
        workingPlace.setLandTwo(commonCodeService.commonMobileNumberLengthValidator(workingPlace.getEmailTwo()));
        workingPlace.setLandThree(commonCodeService.commonMobileNumberLengthValidator(workingPlace.getLandThree()));
        workingPlace.setLandFour(commonCodeService.commonMobileNumberLengthValidator(workingPlace.getLandFour()));
        workingPlace.setFaxNumber(commonCodeService.commonMobileNumberLengthValidator(workingPlace.getFaxNumber()));
        workingPlaceService.persist(workingPlace);
        return "redirect:/workingPlace";
    }

    /*
     * delete working place from database
     * */
    @GetMapping( value = "/remove/{id}" )
    public String removeWorkingPlace(@PathVariable Long id) {
        workingPlaceService.delete(id);
        return "redirect:/workingPlace";
    }

    /*
     * search working place in the database
     * */
    @GetMapping( value = "/search" )
    public String search(Model model, WorkingPlace workingPlace) {
        model.addAttribute("workingPlaceDetail", workingPlaceService.search(workingPlace));
        return "workingPlace/workingPlace-detail";
    }
}