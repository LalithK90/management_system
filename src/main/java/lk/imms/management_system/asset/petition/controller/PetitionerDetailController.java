package lk.imms.management_system.asset.petition.controller;

import lk.imms.management_system.asset.petition.entity.PetitionerDetail;
import lk.imms.management_system.asset.petition.service.PetitionerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/petitionerDetail" )
public class PetitionerDetailController {
    private final PetitionerDetailService petitionerDetailService;

    @Autowired
    public PetitionerDetailController(PetitionerDetailService petitionerDetailService) {
        this.petitionerDetailService = petitionerDetailService;
    }

    public List< PetitionerDetail > findAll() {
        return petitionerDetailService.findAll();
    }
}
