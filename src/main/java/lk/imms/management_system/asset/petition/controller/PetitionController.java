package lk.imms.management_system.asset.petition.controller;

import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/petition" )
public class PetitionController {
    private final PetitionService petitionService;

    @Autowired
    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    public List< Petition > findAll() {
        return petitionService.findAll();
    }
}
