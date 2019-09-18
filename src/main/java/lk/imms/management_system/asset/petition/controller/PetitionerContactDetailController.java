package lk.imms.management_system.asset.petition.controller;

import lk.imms.management_system.asset.petition.entity.PetitionerContactDetail;
import lk.imms.management_system.asset.petition.service.PetitionerContactDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/petitionerContactDetail" )
public class PetitionerContactDetailController {
    private final PetitionerContactDetailService petitionerContactDetailService;

    @Autowired
    public PetitionerContactDetailController(PetitionerContactDetailService petitionerContactDetailService) {
        this.petitionerContactDetailService = petitionerContactDetailService;
    }

    public List< PetitionerContactDetail > findAll(){
        return petitionerContactDetailService.findAll();
    }
}
