package lk.imms.management_system.asset.court.controller;

import lk.imms.management_system.asset.court.entity.Court;
import lk.imms.management_system.asset.court.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping( "/court" )
public class CourtController {
    private final CourtService courtService;

    @Autowired
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @RequestMapping
    public String courtPage(Model model) {
        model.addAttribute("courts", courtService.findAll());
        return "court/court";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String courtView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("courtDetail", courtService.findById(id));
        return "court/court-detail";
    }

    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET )
    public String editCourtFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("court", courtService.findById(id));
        model.addAttribute("addStatus", false);
        return "court/addCourt";
    }

    @RequestMapping( value = "/add", method = RequestMethod.GET )
    public String courtAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("court", new Court());
        return "court/addCourt";
    }


    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String addCourt(@Valid @ModelAttribute Court court, BindingResult result, Model model) {
        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            model.addAttribute("court", court);
            return "court/addCourt";
        }
        try {
            courtService.persist(court);
            return "redirect:/court";
        } catch ( Exception e ) {
            ObjectError error = new ObjectError("court",
                                                "This court is already in the System <br/>System message -->" + e.toString());
            result.addError(error);
            model.addAttribute("addStatus", false);
            model.addAttribute("court", court);
            return "court/addCourt";
        }
    }


    @RequestMapping( value = "/remove/{id}", method = RequestMethod.GET )
    public String removeCourt(@PathVariable Long id) {
        courtService.delete(id);
        return "redirect:/court";
    }

    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public String search(Model model, Court court) {
        model.addAttribute("courtDetail", courtService.search(court));
        return "court/court-detail";
    }
}
