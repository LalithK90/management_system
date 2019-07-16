package lk.imms.management_system.general.controller;

import lk.imms.management_system.general.security.entity.PasswordChange;
import lk.imms.management_system.general.security.entity.User;
import lk.imms.management_system.general.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProfileController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(Model model, Principal principal) {
        model.addAttribute("addStatus", true);
        model.addAttribute("employeeDetail", userService.findByUserName(principal.getName()).getEmployee());
        return "employee/employee-detail";
    }

    @RequestMapping(value = "/passwordChange", method = RequestMethod.GET)
    public String passwordChangeForm(Model model) {
        model.addAttribute("pswChange", new PasswordChange());
        return "login/passwordChange";
    }

    @RequestMapping(value = "/passwordChange", method = RequestMethod.POST)
    public String passwordChange(@Valid @ModelAttribute PasswordChange passwordChange, Model model, BindingResult result) {
        User user = userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
        System.out.println(passwordChange.toString());
        if (passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword()))
            if (!result.hasErrors()) {
                if (passwordChange.getNewPassword().equals(passwordChange.getNewPasswordConform())) {
                    user.setPassword(passwordChange.getNewPassword());
                    userService.persist(user);
                    System.out.println("user password update");
                    model.addAttribute("message", "Successfully Change Your Password");
                    model.addAttribute("alert", true);
                    return "fragments/alert";
                }
            }
        return "redirect:/passwordChange";
    }
}