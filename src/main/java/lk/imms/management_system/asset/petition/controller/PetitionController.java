package lk.imms.management_system.asset.petition.controller;


import lk.imms.management_system.asset.employee.controller.EmployeeRestController;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.minute.entity.Enum.MinuteState;
import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.minute.entity.MinutePetitionFiles;
import lk.imms.management_system.asset.minute.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minute.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionPriority;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionerType;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import lk.imms.management_system.asset.petitioner.controller.PetitionerRestController;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// This clz is used to manage petition adding process while on this adding
// Minute, petition, and petitioner details come on one same object MinutePetition
@Controller
@RequestMapping( "/petition" )
public class PetitionController {
    private final PetitionService petitionService;
    private final MinutePetitionFilesService minutePetitionFilesService;
    private final PetitionStateService petitionStateService;
    private final MinutePetitionService minutePetitionService;
    private final PetitionerService petitionerService;

    @Autowired
    public PetitionController(PetitionService petitionService, MinutePetitionFilesService minutePetitionFilesService,
                              PetitionStateService petitionStateService, MinutePetitionService minutePetitionService,
                              PetitionerService petitionerService) {
        this.petitionService = petitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionStateService = petitionStateService;
        this.minutePetitionService = minutePetitionService;
        this.petitionerService = petitionerService;
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
        String[] arg = {"designation", "id"};
        model.addAttribute("employeeUrl", MvcUriComponentsBuilder
                .fromMethodName(EmployeeRestController.class, "getEmployeeByWorkingPlace", arg)
                .build()
                .toString());
        model.addAttribute("petitionerUrl", MvcUriComponentsBuilder
                .fromMethodName(PetitionerRestController.class, "getPetitioner", "")
                .build()
                .toString());
        return "petition/addPetition";
    }

    //To get files from the database
/*
    public void petitionFiles(Petition petition, Model model) {
        List< FileInfo > fileInfos = minutePetitionFilesService.findByPetition(petition)
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
        PetitionFiles file = minutePetitionFilesService.findByNewID(filename);
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
    @GetMapping( "/add" )
    public String addPetitionPage(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("petition", new Petition());
        return commonThings(model);
    }

    @PostMapping( value = {"/add", "/update"} )
    public String persistPetition(@Valid @ModelAttribute( "petition" ) Petition petition, Model model,
                                  BindingResult result) throws IOException {
        String petitionNumber = "IMMSHQ100";
        String indexNumber = "IMMS1000";
        petition.setPetitionNumber(petitionNumber);
        petition.setIndexNumber(indexNumber);
        Petition savedPetition = new Petition();
        //Todo - > Need to create petition number and index number
        savedPetition.setPetitionNumber(petition.getPetitionNumber());
        savedPetition.setIndexNumber(petition.getIndexNumber());
        savedPetition.setVillage(petition.getVillage());
        savedPetition.setAgaDivision(petition.getAgaDivision());
        savedPetition.setSubject(petition.getSubject());
        savedPetition.setPetitionType(petition.getPetitionType());
        savedPetition.setPetitionPriority(petition.getPetitionPriority());

        if ( petition.getPetitioner().getId() != null ) {
            savedPetition.setPetitioner(petition.getPetitioner());
        } else {
            Petitioner petitioner = petition.getPetitioner();
            petitioner.setPetitionerType(PetitionerType.OTHER);
            savedPetition.setPetitioner(petitionerService.persist(petitioner));
        }

        savedPetition.setWorkingPlace(petition.getWorkingPlace());
        //first petition state save and get petition
        savedPetition = petitionService.persist(petition);

        // 1. PetitionState
        PetitionState petitionState = new PetitionState();
        if ( savedPetition != null ) {
            petitionState.setPetition(savedPetition);
            petitionState.setPetitionStateType(PetitionStateType.RECEIVED);
            // initial petition state is saved
            petitionStateService.persist(petitionState);
        }

        // 2. Minute petition.
        for ( MinutePetition minutePetition : petition.getMinutePetitions() ) {
            minutePetition.setPetition(savedPetition);
            minutePetition.setMinuteState(MinuteState.CURRENTSITUATION);
            // Minute Petition Files
            List< MinutePetitionFiles > storedFile = new ArrayList<>();
            //if there is nothing to save files
            if ( !petition.getFiles().isEmpty() ) {
                for ( MultipartFile file : petition.getFiles() ) {
                    MinutePetitionFiles minutePetitionFile =
                            minutePetitionFilesService.findByName(file.getOriginalFilename());
                    if ( minutePetitionFile != null ) {
                        // update new contents
                        minutePetitionFile.setPic(file.getBytes());
                    } else {
                        assert savedPetition != null;
                        minutePetitionFile = new MinutePetitionFiles(file.getOriginalFilename(),
                                                                     file.getContentType(),
                                                                     file.getBytes(),
                                                                     savedPetition.getPetitionNumber().concat("-" + LocalDateTime.now()),
                                                                     UUID.randomUUID().toString().concat(
                                                                             "minutePetition"));
                    }
                    minutePetitionFile.setMinutePetition(minutePetitionService.persist(minutePetition));
                    storedFile.add(minutePetitionFile);
                }
                minutePetitionFilesService.persist(storedFile);
            } else {
                minutePetitionService.persist(minutePetition);
            }
        }


        return "redirect:/petition/add";
    }
}

// -->Auto Generate Year/Month/OfficeType/StationCode/PetitionNumberFromDB
