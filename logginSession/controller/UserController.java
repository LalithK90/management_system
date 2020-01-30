package lk.imms.management_system.util.logginSession.controller;

import lk.imms.management_system.util.logginSession.entity.ActiveUserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    ActiveUserStore activeUserStore;

/*    @RequestMapping(value = "/loggedUsers", method = RequestMethod.GET)
    public String getLoggedUsers(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }*/
}