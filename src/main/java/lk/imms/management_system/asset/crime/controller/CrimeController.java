package lk.imms.management_system.asset.crime.controller;

import lk.imms.management_system.asset.court.service.CourtService;
import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.crime.service.CrimeService;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/crime" )
public class CrimeController {
    private final CrimeService crimeService;
    private final CourtService courtService;
    private final DetectionTeamService detectionTeamService;

    @Autowired
    public CrimeController(CrimeService crimeService, CourtService courtService,
                           DetectionTeamService detectionTeamService) {
        this.crimeService = crimeService;
        this.courtService = courtService;
        this.detectionTeamService = detectionTeamService;
    }

    @GetMapping
    public List< Crime > findAll(Model model) {
        model.addAttribute("crimes",crimeService.findAll());
        return crimeService.findAll();

    }

    @GetMapping( "{id}" )
    public String addForm(Model model, @PathVariable Long id) {
        Crime crime = new Crime();
        crime.setDetectionTeam(detectionTeamService.findById(id));
        model.addAttribute("courts", courtService.findAll());
        model.addAttribute("crime", crime);
        return "crime/addCrime";
    }


}
