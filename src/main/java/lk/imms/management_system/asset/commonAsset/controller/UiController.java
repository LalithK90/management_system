package lk.imms.management_system.asset.commonAsset.controller;

import lk.imms.management_system.asset.userManagement.entity.Enum.UserSessionLogStatus;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.entity.UserSessionLog;
import lk.imms.management_system.asset.userManagement.service.UserSessionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    private final UserSessionLogService userSessionLogService;

    @Autowired
    public UiController(UserSessionLogService userSessionLogService) {
        this.userSessionLogService = userSessionLogService;
    }

    @GetMapping( value = {"/", "/index"} )
    public String index() {
        return "index";
    }

    @GetMapping( value = {"/home", "/mainWindow"} )
    public String getHome() {
        return "mainWindow";
    }

    @GetMapping( value = {"/login"} )
    public String getLogin() {
        return "login/login";
    }

    @GetMapping( value = {"/login/error10"} )
    public String getLogin10(Model model) {
        model.addAttribute("err", "You already entered wrong credential more than 10 times. \n Please meet the system" +
                " admin");
        return "login/login";
    }

    @GetMapping( value = {"/login/noUser"} )
    public String getLoginNoUser(Model model) {
        model.addAttribute("err", "There is no user according to the user name. \n Please try again !!");
        return "login/login";
    }

    @GetMapping( value = {"/unicodeTamil"} )
    public String getUnicodeTamil() {
        return "fragments/unicodeTamil";
    }

    @GetMapping( value = {"/unicodeSinhala"} )
    public String getUnicodeSinhala() {
        return "fragments/unicodeSinhala";
    }
}