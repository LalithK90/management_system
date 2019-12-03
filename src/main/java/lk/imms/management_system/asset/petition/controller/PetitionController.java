package lk.imms.management_system.asset.petition.controller;


import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionPriority;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.service.PetitionFilesService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
// This clz is used to manage petition adding process while on this adding
// Minute, petition, and petitioner details come on one same object MinutePetition
@Controller
@RequestMapping( "/petition" )
public class PetitionController {
    private final PetitionService petitionService;
    private final PetitionFilesService petitionFilesService;

    @Autowired
    public PetitionController(PetitionService petitionService, PetitionFilesService petitionFilesService) {
        this.petitionService = petitionService;
        this.petitionFilesService = petitionFilesService;
    }

    // Common things for petition add and update
    private String commonThings(Model model) {
        model.addAttribute("petitionTypes", PetitionType.values());
        model.addAttribute("petitionPriorities", PetitionPriority.values());
        model.addAttribute("designations", Designation.values());
        model.addAttribute("provinces", Province.values());
        model.addAttribute("districtUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                .build()
                .toString());
        model.addAttribute("stationUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                .build()
                .toString());
        return "petition/addPetition";
    }

    //To get files from the database
/*
    public void petitionFiles(Petition petition, Model model) {
        List< FileInfo > fileInfos = petitionFilesService.findByPetition(petition)
                .stream()
                .map(PetitionFiles -> {
                    String filename = PetitionFiles.getName();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(PetitionController.class, "downloadFile", PetitionFiles.getNewId())
                            .build()
                            .toString();
                    return new FileInfo(filename, PetitionFiles.getCreatedAt(), url);
                })
                .collect(Collectors.toList());
        model.addAttribute("files", fileInfos);
    }
*/

    //When scr called file will send to
/*
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        PetitionFiles file = petitionFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }
*/
//Give all available petition according to login user
    @GetMapping
    public String petitionPage(Model model) {
        //todo -> get user from principal object find his working place
        // and rank to display his belongs petition
        model.addAttribute("petitions", petitionService.findAll());
        return "petition/petition";
    }
//Give a frontend to petition add from
    @GetMapping("/add")
    public String addPetitionPage(Model model) {
        model.addAttribute("addStatus",true);
        model.addAttribute("minutePetition", new Petition());
        return commonThings(model);
    }
}

// -->Auto Generate Year/Month/OfficeType/StationCode/PetitionNumberFromDB
