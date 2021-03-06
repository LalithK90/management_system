package lk.imms.management_system.asset.contravene.controller;

import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping( "/contravene" )
public class ContraveneController {
    private final ContraveneService contraveneService;

    public ContraveneController(ContraveneService contraveneService) {
        this.contraveneService = contraveneService;
    }

    @RequestMapping
    public String contravenePage(Model model) {
        model.addAttribute("contravenes", contraveneService.findAll());
        return "contravene/contravene";
    }

    @GetMapping( value = "/{id}" )
    public String contraveneView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("contraveneDetail", contraveneService.findById(id));
        return "contravene/contravene-detail";
    }

    @GetMapping( value = "/edit/{id}" )
    public String editContraveneFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("contravene", contraveneService.findById(id));
        model.addAttribute("addStatus", false);
        return "contravene/addContravene";
    }

    @GetMapping( value = "/add" )
    public String contraveneAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("contravene", new Contravene());
        return "contravene/addContravene";
    }


    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @PostMapping( value = {"/add", "/update"} )
    public String addContravene(@Valid @ModelAttribute Contravene contravene, BindingResult result, Model model) {
        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            model.addAttribute("contravene", contravene);
            return "contravene/addContravene";
        }
        try {
            contraveneService.persist(contravene);
            return "redirect:/contravene";
        } catch ( Exception e ) {
            ObjectError error = new ObjectError("role",
                                                "This role is already in the System <br/>System message -->" + e.toString());
            result.addError(error);
            model.addAttribute("contravene", contravene);
            model.addAttribute("addStatus", false);
            return "contravene/addContravene";
        }
    }


    @GetMapping( value = "/remove/{id}" )
    public String removeContravene(@PathVariable Long id) {
        contraveneService.delete(id);
        return "redirect:/contravene";
    }

    @GetMapping( value = "/search" )
    public String search(Model model, Contravene contravene) {
        model.addAttribute("contraveneDetail", contraveneService.search(contravene));
        return "contravene/contravene-detail";
    }
}
