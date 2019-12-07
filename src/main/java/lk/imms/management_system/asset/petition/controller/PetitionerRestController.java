package lk.imms.management_system.asset.petition.controller;

import lk.imms.management_system.asset.petition.entity.Enum.PetitionerType;
import lk.imms.management_system.asset.petition.entity.Petitioner;
import lk.imms.management_system.asset.petition.service.PetitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;

@RestController
@RequestMapping( "/petitioner" )
public class PetitionerRestController {
    private final PetitionerService petitionerService;

    @Autowired
    public PetitionerRestController(PetitionerService petitionerService) {
        this.petitionerService = petitionerService;
    }

    @GetMapping( "/{petitionerType}" )
    private Petitioner getPetitioner(@PathVariable( "petitionerType" ) PetitionerType petitionerType) {
    return petitionerService.findByPetitionerType(petitionerType);
    }
}
