package lk.imms.management_system.asset.commonAsset.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
    @GetMapping( value = {"/","/index"} )
    public String index() {
        return "index";
    }

    @GetMapping( value = {"/home", " /mainWindow"} )
    public String getHome() {
        return "mainWindow";
    }

    @GetMapping( value = {"/login"} )
    public String getLogin() {
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