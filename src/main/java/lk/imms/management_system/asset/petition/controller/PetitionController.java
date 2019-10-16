package lk.imms.management_system.asset.petition.controller;


import lk.imms.management_system.asset.petition.entity.Enum.PetitionPriority;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.asset.petition.service.PetitionFilesService;
import lk.imms.management_system.asset.petition.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // common things for petition add and update
    private void commonThings(Model model) {
        model.addAttribute("title", PetitionType.values());
        model.addAttribute("gender", PetitionPriority.values());
        model.addAttribute("gender", PetitionPriority.values());
    }

    //to get files from the database
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

    //when scr called file will send to
/*
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        PetitionFiles file = petitionFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }
*/

    @RequestMapping
    public String petitionPage(Model model) {
        model.addAttribute("petitions", petitionService.findAll());
        return "petition/petition";
    }

    @RequestMapping("/add")
    public String addPetitionPage(Model model) {
        model.addAttribute("petitions", petitionService.findAll());
        return "petition/addPetition";
    }
}
