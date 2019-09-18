package lk.imms.management_system.asset.crime.controller;

import lk.imms.management_system.asset.crime.entity.Court;
import lk.imms.management_system.asset.crime.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/court" )
public class CourtController {
    private final CourtService courtService;
//todo-> need more method
    @Autowired
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    public List< Court > findAll() {
        return courtService.findAll();
    }
}
