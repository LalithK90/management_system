package lk.imms.management_system.asset.offenders.controller;

import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.asset.offenders.service.OffenderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/offender" )
public class OffenderController {
    private final OffenderService offenderService;

    public OffenderController(OffenderService offenderService) {
        this.offenderService = offenderService;
    }

    public List< Offender > findAll() {
        return offenderService.findAll();
    }
}
