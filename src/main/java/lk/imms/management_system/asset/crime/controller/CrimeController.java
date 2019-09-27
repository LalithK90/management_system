package lk.imms.management_system.asset.crime.controller;

import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.crime.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/crime" )
public class CrimeController {
    private final CrimeService crimeService;

    @Autowired
    public CrimeController(CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @GetMapping
    public List< Crime > findAll() {
        return crimeService.findAll();

    }
}
