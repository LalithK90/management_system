package lk.imms.management_system.asset.commonAsset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping( value = {"/home", "/mainWindow"} )
    public String getHome() {
        return "/mainWindow";
    }

    @GetMapping( value = {"/", "/login"} )
    public String getLogin() {
        System.out.println("  \n\n\n" + passwordEncoder.encode("asanka") + "\n\n\n\n");

        return "login/login";
    }
}
