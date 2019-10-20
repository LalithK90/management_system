package lk.imms.management_system.asset.userManagement.controller;


import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.util.service.DateTimeAgeService;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final EmployeeService employeeService;
    private final DateTimeAgeService dateTimeAgeService;

    @Autowired
    public UserController(UserService userService, EmployeeService employeeService, DateTimeAgeService dateTimeAgeService, RoleService roleService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.roleService = roleService;
    }

    @RequestMapping
    public String userPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/user";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String userView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userDetail", userService.findById(id));
        model.addAttribute("employee", userService.findById(id).getEmployee());
        return "user/user-detail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUserFrom(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("addStatus", false);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("employee", employeeService.findAll());
        return "user/addUser";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String userAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("employee", employeeService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new User());
        return "user/addUser";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {

        if (userService.findUserByEmployee(user.getEmployee()) != null) {
            ObjectError error = new ObjectError("employee", "This employee already defined as a user");
            result.addError(error);
        }
        if (result.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("employee", employeeService.findAll());
            model.addAttribute("user", user);
            return "user/addUser";
        }
        if (user.isEnabled()) {
            user.setCreatedDate(dateTimeAgeService.getCurrentDate());
            user.setEnabled(true);
            userService.persist(user);
        } else {
            user.setCreatedDate(dateTimeAgeService.getCurrentDate());
            user.setEnabled(true);
            System.out.println(  userService.persist(user));
        }
        return "redirect:/user";
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/user";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, User user) {
        model.addAttribute("userDetail", userService.search(user));
        return "user/user-detail";
    }
}
