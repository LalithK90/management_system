package lk.imms.management_system.asset.contravene.controller;

import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/contravene" )
public class ContraveneController {
    private final ContraveneService contraveneService;

    public ContraveneController(ContraveneService contraveneService) {
        this.contraveneService = contraveneService;
    }

    public List< Contravene > findAll() {
        return contraveneService.findAll();
    }
}
