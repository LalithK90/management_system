package lk.imms.management_system.asset.userManagement.controller;


import lk.imms.management_system.asset.userManagement.entity.Role;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/role" )
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /*
     * Send all role to font end
     * */
    @RequestMapping
    public String rolePage(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "role/role";
    }

    /*
     * Send one-{find using id} role details to font-end view
     * */
    @GetMapping( value = "/{id}" )
    public String roleView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("addStatus", false);
        return "role/addRole";
    }

    /*
     * Send one-{find using id} role to font-end to edit
     * */
    @GetMapping( value = "/edit/{id}" )
    public String editRoleFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("addStatus", false);
        return "role/addRole";
    }

    /*
     * Send form view role to font-end to add new role
     * */
    @GetMapping( value = {"/add"} )
    public String roleAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("role", new Role());
        return "role/addRole";
    }
    /*
     * New Working palace add and stored role edit using following method
     * */

    @PostMapping( value = {"/add", "/update"} )
    public String addRole(@Valid @ModelAttribute Role role, BindingResult result, Model model
            , RedirectAttributes redirectAttributes) {

        if ( result.hasErrors() && role.getId() == null ) {
            model.addAttribute("addStatus", true);
            model.addAttribute("role", role);
            return "role/addRole";
        }

        try {
            roleService.persist(role);
            return "redirect:/role";
        } catch ( Exception e ) {
            ObjectError error = new ObjectError("role",
                                                "This role is already in the System <br/>System message -->" + e.toString());
            result.addError(error);
            model.addAttribute("addStatus", false);
            model.addAttribute("role", role);
            return "role/addRole";
        }

    }

    /*
     * delete role from database
     * */
    @GetMapping( value = "/remove/{id}")
    public String removeRole(@PathVariable( "id" ) Long id) {
        roleService.delete(id);
        return "redirect:/role";
    }

    /*
     * search role in the database
     * */
    @GetMapping( value = "/search" )
    public String search(Model model, Role role) {
        model.addAttribute("role", roleService.search(role));
        return "role/role";
    }
}
