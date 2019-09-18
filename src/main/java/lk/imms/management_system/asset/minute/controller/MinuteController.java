package lk.imms.management_system.asset.minute.controller;

import lk.imms.management_system.asset.minute.entity.Minute;
import lk.imms.management_system.asset.minute.service.MinuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/minute" )
public class MinuteController {
    private final MinuteService minuteService;

    @Autowired
    public MinuteController(MinuteService minuteService) {
        this.minuteService = minuteService;
    }

    private List< Minute > findAll() {
        return minuteService.findAll();
    }
}
