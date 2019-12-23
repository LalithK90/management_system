package lk.imms.management_system.asset.offenders.controller;

import lk.imms.management_system.asset.offenders.service.OffenderCallingNameService;
import lk.imms.management_system.asset.offenders.service.OffenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OffenderRestController {
    private final OffenderService offenderService;
    private final OffenderCallingNameService offenderCallingNameService;

    @Autowired
    public OffenderRestController(OffenderService offenderService,
                                  OffenderCallingNameService offenderCallingNameService) {
        this.offenderService = offenderService;
        this.offenderCallingNameService = offenderCallingNameService;
    }
}
