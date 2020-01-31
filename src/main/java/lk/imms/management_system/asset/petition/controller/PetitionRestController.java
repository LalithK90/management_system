package lk.imms.management_system.asset.petition.controller;

        import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
        import lk.imms.management_system.asset.userManagement.entity.User;
        import lk.imms.management_system.asset.userManagement.service.UserService;
        import lk.imms.management_system.util.service.DateTimeAgeService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RestController;

        import java.time.LocalDate;

@RestController
public class PetitionRestController {
    private final UserService userService;
    private final MinutePetitionService minutePetitionService;
    private final DateTimeAgeService dateTimeAgeService;

    @Autowired
    public PetitionRestController(UserService userService, MinutePetitionService minutePetitionService,
                                  DateTimeAgeService dateTimeAgeService) {
        this.userService = userService;
        this.minutePetitionService = minutePetitionService;
        this.dateTimeAgeService = dateTimeAgeService;
    }

    @GetMapping( "/petitionCount" )
    private int petitionCount() {
        //do some logic here if you want something to be done whenever
        User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        return minutePetitionService.findByEmployeeAndCreatedAtBetween(authUser.getEmployee(),
                                                                       dateTimeAgeService.dateTimeToLocalDateStartInDay(LocalDate.now()), dateTimeAgeService.dateTimeToLocalDateEndInDay(LocalDate.now())).size();
    }
}
