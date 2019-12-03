package lk.imms.management_system.asset.minute.controller;

import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.minute.service.MinutePetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/minute" )
public class MinuteController {
    private final MinutePetitionService minutePetitionService;

    @Autowired
    public MinuteController(MinutePetitionService minutePetitionService) {
        this.minutePetitionService = minutePetitionService;
    }

    private List< MinutePetition > findAll() {
        return minutePetitionService.findAll();
    }
}
