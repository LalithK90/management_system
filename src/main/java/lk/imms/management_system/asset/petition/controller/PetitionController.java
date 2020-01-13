package lk.imms.management_system.asset.petition.controller;


import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.minutePetition.entity.Enum.MinuteState;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetitionFiles;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionPriority;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import lk.imms.management_system.asset.petitioner.controller.PetitionerRestController;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.service.MakeAutoGenerateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
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
    private final UserService userService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final CommonCodeService commonCodeService;

    @Autowired
    public PetitionController(PetitionService petitionService, MinutePetitionFilesService minutePetitionFilesService,
                              PetitionStateService petitionStateService, MinutePetitionService minutePetitionService,
                              PetitionerService petitionerService, UserService userService,
                              MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                              CommonCodeService commonCodeService) {
        this.petitionService = petitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionStateService = petitionStateService;
        this.minutePetitionService = minutePetitionService;
        this.petitionerService = petitionerService;
        this.userService = userService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.commonCodeService = commonCodeService;
    }

    // Common things for petition add and update
    private String commonThings(Model model) {
        model.addAttribute("petitionTypes", PetitionType.values());
        model.addAttribute("petitionPriorities", PetitionPriority.values());
        commonCodeService.commonUrlBuilder(model);
        model.addAttribute("petitionerUrl", MvcUriComponentsBuilder
                .fromMethodName(PetitionerRestController.class, "getPetitioner", "")
                .build()
                .toString());
        return "petition/addPetition";
    }

    //When scr called file will send to
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        MinutePetitionFiles file = minutePetitionFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    //common code from petitions list
    private String commonCodeFromPetitionList(Model model, List< Petition > petitions) {
        //get current login user
        User currentUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

 /*       model.addAttribute("petitions",
                           petitions
                                   .stream()
                                   .filter((x) -> {
                                       boolean matched = false;
                                       for ( WorkingPlace workingPlace : currentUser.getWorkingPlaces() ) {
                                           matched = x.getWorkingPlace().equals(workingPlace);
                                       }
                                       return matched;
                                   })
                                   .collect(Collectors.toList()));*/
        model.addAttribute("petitions", petitions);
        return "petition/petition";
    }


    //Give all available petition according to login user
    @GetMapping
    public String petitionPage(Model model) {
        return commonCodeFromPetitionList(model, petitionService.findAll());
    }

    //petition details
    @GetMapping( "/view/{id}" )
    public String viewPetition(@PathVariable Long id) {
        //TODO-> need to view petition details
        return "petition/petition-detail";
    }

    //Give a frontend to petition add from
    @GetMapping( "/add" )
    public String addPetitionPage(Model model) {
        model.addAttribute("petition", new Petition());
        return commonThings(model);
    }

    @PostMapping( "/add" )
    public String persistPetition(@Valid @ModelAttribute( "petition" ) Petition petition, Model model,
                                  BindingResult result) throws IOException {
        //get current login user
        User currentUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        if ( result.hasErrors() ) {
            model.addAttribute("petition", petition);
            return commonThings(model);
        }

        WorkingPlace workingPlace = currentUser.getWorkingPlaces().get(0);
        if ( petition.getId() == null ) {
            String petitionNumber;
            String indexNumber;
            if ( petitionService.getLastOne() == null ) {
                petitionNumber =
                        makeAutoGenerateNumberService.numberAutoGen(null).toString();
                indexNumber =
                        makeAutoGenerateNumberService.numberAutoGen(null).toString();

            } else {
                petitionNumber =
                        makeAutoGenerateNumberService.numberAutoGen(petitionService.getLastOne().getIndexNumber()).toString();
                indexNumber =
                        makeAutoGenerateNumberService.numberAutoGen(petitionService.getLastOne().getIndexNumber()).toString();

            }
            petition.setPetitionNumber(petitionNumber + "/" + workingPlace.getWorkingPlaceType() + "/" + workingPlace.getCode());
            petition.setIndexNumber(indexNumber);
        }

        Petition savedPetition = new Petition();
        savedPetition.setPetitionNumber(petition.getPetitionNumber());
        savedPetition.setIndexNumber(petition.getIndexNumber());
        savedPetition.setVillage(petition.getVillage());
        savedPetition.setAgaDivision(petition.getAgaDivision());
        savedPetition.setSubject(petition.getSubject());
        savedPetition.setPetitionType(petition.getPetitionType());
        savedPetition.setPetitionPriority(petition.getPetitionPriority());

        if ( petition.getPetitioner().getId() != null ) {
            savedPetition.setPetitioner(petitionerService.findById(petition.getPetitioner().getId()));
        } else {
            Petitioner petitioner = petition.getPetitioner();
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
//save minute petition before
            MinutePetition minutePetition1 = minutePetitionService.persist(minutePetition);

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
                    minutePetitionFile.setMinutePetition(minutePetition1);
                    minutePetitionFilesService.save(minutePetitionFile);
                }
            }

        }
        return "redirect:/petition/add";
    }


    //common code for search from
    private String commonCodeForSearch(Model model, Petition petition) {
        model.addAttribute("petition", petition);
        model.addAttribute("petitionTypes", PetitionType.values());
        model.addAttribute("petitionPriorities", PetitionPriority.values());
        return "petition/petitionSearch";
    }

    @GetMapping( "/search" )
    public String searchForm(Model model) {
        return commonCodeForSearch(model, new Petition());
    }

    @PostMapping( "/search" )
    public String searchedPetitions(@ModelAttribute Petition petition, Model model, BindingResult result) {
        if ( result.hasErrors() && petition == null ) {
            ObjectError objectError = new ObjectError("petition", "There is not details what you did not provide " +
                    "petition\n if you do not mind, cloud you please provide details.. ");
            result.addError(objectError);
            return commonCodeForSearch(model, petition);
        }
        return commonCodeFromPetitionList(model, petitionService.search(petition));
    }
}
