package lk.imms.management_system.asset.workingPlace.controller;

import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.Enum.WorkingPlaceType;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/workingPlace" )
public class WorkingPlaceController {

    private final WorkingPlaceService workingPlaceService;

    @Autowired
    public WorkingPlaceController(WorkingPlaceService workingPlaceService) {
        this.workingPlaceService = workingPlaceService;
    }

    // common font-end attribute for workingPlace
    private void CommonThings(Model model) {
        model.addAttribute("province", Province.values());
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
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String workingPlaceView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("workingPlace", workingPlaceService.findById(id));
        model.addAttribute("addStatus", false);
        return "workingPlace/workingPlace-detail";
    }

    /*
     * Send one-{find using id} working place to font-end to edit
     * */
    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET )
    public String editWorkingPlaceFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("workingPlace", workingPlaceService.findById(id));
        model.addAttribute("addStatus", false);
        CommonThings(model);
        return "workingPlace/addWorkingPlace";
    }

    /*
     * Send form view working place to font-end to add new working place
     * */
    @RequestMapping( value = {"/add"}, method = RequestMethod.GET )
    public String workingPlaceAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("workingPlace", new WorkingPlace());
        CommonThings(model);
        return "workingPlace/addWorkingPlace";
    }
    /*
     * New Working palace add and stored working place edit using following method
     * */

    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String addWorkingPlace(@Valid @ModelAttribute WorkingPlace workingPlace, BindingResult result, Model model
            , RedirectAttributes redirectAttributes) {

        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("workingPlace", workingPlace);
            CommonThings(model);
            return "workingPlace/addWorkingPlace";
        }
        try {
            workingPlaceService.persist(workingPlace);
            return "redirect:/workingPlace";
        } catch ( DataIntegrityViolationException e ) {
            ObjectError error = new ObjectError("workingPlace", "This working place is already added to the system. ");
            result.addError(error);
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("workingPlace", workingPlace);
            CommonThings(model);
        }
        return "workingPlace/addWorkingPlace";
    }

    /*
     * delete working place from database
     * */
    @RequestMapping( value = "/remove/{id}", method = RequestMethod.GET )
    public String removeWorkingPlace(@PathVariable Long id) {
        workingPlaceService.delete(id);
        return "redirect:/workingPlace";
    }

    /*
     * search working place in the database
     * */
    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public String search(Model model, WorkingPlace workingPlace) {
        model.addAttribute("workingPlaceDetail", workingPlaceService.search(workingPlace));
        return "workingPlace/workingPlace-detail";
    }
}