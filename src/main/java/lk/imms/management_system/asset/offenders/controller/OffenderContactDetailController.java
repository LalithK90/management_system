package lk.imms.management_system.asset.offenders.controller;

import lk.imms.management_system.asset.offenders.entity.OffenderContactDetail;
import lk.imms.management_system.asset.offenders.service.OffenderContactDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/offenderContactDetail" )
public class OffenderContactDetailController {
    private final OffenderContactDetailService offenderContactDetailService;

    @Autowired
    public OffenderContactDetailController(OffenderContactDetailService offenderContactDetailService) {
        this.offenderContactDetailService = offenderContactDetailService;
    }

    public List< OffenderContactDetail > findAll() {
        return offenderContactDetailService.findAll();
    }
}
