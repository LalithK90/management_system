package lk.imms.management_system.asset.offenders.controller;

import lk.imms.management_system.asset.offenders.entity.OffenderCallingName;
import lk.imms.management_system.asset.offenders.service.OffenderCallingNameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/offenderCallingName")
public class OffenderCallingNameController {
    private final OffenderCallingNameService offenderCallingNameService;

    public OffenderCallingNameController(OffenderCallingNameService offenderCallingNameService) {
        this.offenderCallingNameService = offenderCallingNameService;
    }
    public List< OffenderCallingName > findAll(){
        return offenderCallingNameService.findAll();
    }
}
