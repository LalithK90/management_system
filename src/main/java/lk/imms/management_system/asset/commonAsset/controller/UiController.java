package lk.imms.management_system.asset.commonAsset.controller;

import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.controller.PetitionRestController;
import lk.imms.management_system.asset.petitioner.controller.PetitionerRestController;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.userManagement.service.UserSessionLogService;
import lk.imms.management_system.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;

@Controller
public class UiController {

    private final UserSessionLogService userSessionLogService;
    private final UserService userService;
    private final MinutePetitionService minutePetitionService;
    private final DateTimeAgeService dateTimeAgeService;

    @Autowired
    public UiController(UserSessionLogService userSessionLogService, UserService userService,
                        MinutePetitionService minutePetitionService, DateTimeAgeService dateTimeAgeService) {
        this.userSessionLogService = userSessionLogService;
        this.userService = userService;
        this.minutePetitionService = minutePetitionService;
        this.dateTimeAgeService = dateTimeAgeService;
    }

    @GetMapping( value = {"/", "/index"} )
    public String index() {
        return "index";
    }

    @GetMapping( value = {"/home", "/mainWindow"} )
    public String getHome(Model model) {
        //do some logic here if you want something to be done whenever
        User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("petitions",
                           minutePetitionService
                                   .findByEmployeeAndCreatedAtBetween(authUser.getEmployee(),
                                                                      dateTimeAgeService
                                                                              .dateTimeToLocalDateStartInDay(LocalDate.now()),
                                                                      dateTimeAgeService
                                                                              .dateTimeToLocalDateEndInDay(LocalDate.now())));
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