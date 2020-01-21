package lk.imms.management_system.asset.petition.controller;


import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.service.CommonService;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.crime.service.CrimeService;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeamMember;
import lk.imms.management_system.asset.detectionTeam.service.DetectionTeamService;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.service.EmployeeFilesService;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.minutePetition.entity.Enum.MinuteState;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetitionFiles;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.service.OffenderFilesService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionPriority;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionType;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.asset.petitionAddOffender.service.PetitionOffenderService;
import lk.imms.management_system.asset.petitioner.controller.PetitionerRestController;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.workingPlace.entity.Enum.District;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.Enum.WorkingPlaceType;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
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
    private final UserService userService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final CommonService commonService;
    private final ContraveneService contraveneService;
    private final DetectionTeamService detectionTeamService;
    private final PetitionOffenderService petitionOffenderService;
    private final EmployeeFilesService employeeFilesService;
    private final CrimeService crimeService;
    private final OffenderFilesService offenderFilesService;
    private final EmployeeService employeeService;
    private final WorkingPlaceService workingPlaceService;

    @Autowired
    public PetitionController(PetitionService petitionService, MinutePetitionFilesService minutePetitionFilesService,
                              PetitionStateService petitionStateService, MinutePetitionService minutePetitionService,
                              PetitionerService petitionerService, UserService userService,
                              MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                              CommonService commonService, ContraveneService contraveneService,
                              DetectionTeamService detectionTeamService,
                              PetitionOffenderService petitionOffenderService,
                              EmployeeFilesService employeeFilesService, CrimeService crimeService,
                              OffenderFilesService offenderFilesService, EmployeeService employeeService,
                              WorkingPlaceService workingPlaceService) {
        this.petitionService = petitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionStateService = petitionStateService;
        this.minutePetitionService = minutePetitionService;
        this.petitionerService = petitionerService;
        this.userService = userService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.commonService = commonService;
        this.contraveneService = contraveneService;
        this.detectionTeamService = detectionTeamService;
        this.petitionOffenderService = petitionOffenderService;
        this.employeeFilesService = employeeFilesService;
        this.crimeService = crimeService;
        this.offenderFilesService = offenderFilesService;
        this.employeeService = employeeService;
        this.workingPlaceService = workingPlaceService;
    }

    // Common things for petition add and update
    private String commonThings(Model model) {
        model.addAttribute("petitionTypes", PetitionType.values());
        model.addAttribute("petitionPriorities", PetitionPriority.values());
        commonService.commonUrlBuilder(model);
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
       /* PetitionState petitionState = null;
        for ( Petition petition : petitions ) {
            petitionState = petition.getPetitionStates().get(petition.getPetitionStates().size() - 1);
        }

        if ( petitionState != null ) {
            model.addAttribute("petitionState",petitionState.getPetitionStateType().getPetitionStateType() );
        }*/
        return "petition/petition";
    }


    //Give all available petition according to login user
    @GetMapping
    public String petitionPage(Model model) {
        return commonCodeFromPetitionList(model, petitionService.findAll());
    }

    //petition details
    @GetMapping( "/view/{id}" )
    public String viewPetition(@PathVariable Long id, Model model) {
        Petition petition = petitionService.findById(id);
        //set petition state
        petition.setPetitionStates(petitionStateService.findByPetition(petition));
        model.addAttribute("petition", petition);
        //set minute petition state
        List< MinutePetition > minutePetitions = new ArrayList<>();
        for ( MinutePetition minutePetition : minutePetitionService.findByPetition(petition) ) {
            minutePetition = minutePetitionService.findById(minutePetition.getId());
            if ( minutePetition.getEmployee() != null ) {
                minutePetition.setEmployee(employeeService.findById(minutePetition.getEmployee().getId()));
            }
            if ( minutePetition.getWorkingPlace() != null ) {
                minutePetition.setWorkingPlace(workingPlaceService.findById(minutePetition.getWorkingPlace().getId()));
            }
            minutePetition.setFileInfos(minutePetitionFilesService.minutePetitionFileDownloadLinks(minutePetition));
            minutePetitions.add(minutePetition);
        }
        model.addAttribute("minutePetitions", minutePetitions);
        //detection team
        List< DetectionTeam > detectionTeams = new ArrayList<>();
        for ( DetectionTeam detectionTeam : detectionTeamService.findByPetition(petition) ) {
            //detection team member
            for ( DetectionTeamMember detectionTeamMember : detectionTeam.getDetectionTeamMembers() ) {
                //employee
                Employee employee = employeeService.findById(detectionTeamMember.getEmployee().getId());
                employee.setFileInfos(employeeFilesService.employeeFileDownloadLinks(employee));
                detectionTeamMember.setEmployee(employee);
                //detection team member role
                detectionTeamMember.setDetectionTeamMemberRole(detectionTeamMember.getDetectionTeamMemberRole());
            }
            //detection team note
            detectionTeam.setDetectionTeamNotes(detectionTeam.getDetectionTeamNotes());
            // crime details
            detectionTeam.setCrimes(crimeService.findByDetectionTeam(detectionTeam));
            detectionTeams.add(detectionTeam);
        }
        model.addAttribute("detectionTeams", detectionTeams);
        //petition offender
        List< PetitionOffender > petitionOffenders = new ArrayList<>();
        for ( PetitionOffender petitionOffender : petitionOffenderService.findByPetition(petition) ) {
            Offender offender = petitionOffender.getOffender();

            offender.setFileInfos(offenderFilesService.offenderFileDownloadLinks(offender));
            petitionOffender.setPetition(petition);
            petitionOffender.setContravenes(
                    petitionOffenderService.findByPetitionAndOffender(petition, offender).getContravenes());
            petitionOffenders.add(petitionOffender);
        }
        model.addAttribute("petitionOffenders", petitionOffenders);
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
        //get index number from db and used to it create petition index number and petition number
        String indexNumberDb = petitionService.getLastOne().getIndexNumber();
        String indexNumber =
                makeAutoGenerateNumberService.numberAutoGen(indexNumberDb).toString();
        petition.setPetitionNumber(indexNumber + "/" + workingPlace.getWorkingPlaceType() + "/" + workingPlace.getCode());
        petition.setIndexNumber(indexNumber);


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
        model.addAttribute("workingPlaceTypes", WorkingPlaceType.values());
        model.addAttribute("provinces", Province.values());
        model.addAttribute("districts", District.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("contravenes", contraveneService.findAll());
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
//todo -> need to conform validation for this method
        if ( !petition.getContravenes().isEmpty() ) {
            List< PetitionOffender > petitionOffenders = new ArrayList<>();
            petition.getContravenes().forEach(x ->
                                                      petitionOffenders.addAll(petitionOffenderService
                                                                                       .findByContravenes(x)));
            petition.setPetitionOffenders(petitionOffenders);
        }
        return commonCodeFromPetitionList(model, petitionService.searchAnyParameter(petition));
    }
}
