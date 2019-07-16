package lk.imms.management_system.general.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
    @GetMapping(value = {"/home","/mainWindow"})
    public String getHome() {
        return "/mainWindow";
    }

    @GetMapping(value = {"/","/login"})
    public String getLogin(){
        return "login/login";
    }
}
