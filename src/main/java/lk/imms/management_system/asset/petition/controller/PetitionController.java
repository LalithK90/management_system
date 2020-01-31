package lk.imms.management_system.asset.petition.controller;


import lk.imms.management_system.asset.commonAsset.model.Enum.CivilStatus;
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
import lk.imms.management_system.asset.userManagement.entity.Role;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.workingPlace.entity.Enum.District;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.Enum.WorkingPlaceType;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
import lk.imms.management_system.util.service.EmailService;
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
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final RoleService roleService;
    private final EmailService emailService;

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
                              WorkingPlaceService workingPlaceService, RoleService roleService,
                              EmailService emailService) {
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
        this.roleService = roleService;
        this.emailService = emailService;
    }

    // Common things for petition add and update
    private String commonThings(Model model) {
        model.addAttribute("petitionTypes", PetitionType.values());
        model.addAttribute("petitionPriorities", PetitionPriority.values());
        commonService.commonUrlBuilder(model);
        model.addAttribute("petitionerUrl", MvcUriComponentsBuilder
                .fromMethodName(PetitionRestController.class, "getPetitioner", "")
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

        List< Petition > petitionList = petitions
                .stream()
                .filter((x) -> {
                    boolean matched = false;
                    for ( WorkingPlace workingPlace :
                            currentUser.getWorkingPlaces() ) {
                        matched =
                                x.getWorkingPlace().equals(workingPlace);
                    }
                    return matched;
                }).collect(Collectors.toList());

        minutePetitionService.findAll()
                .stream()
                .filter((x) -> {
                    boolean matched = false;
                    for ( WorkingPlace workingPlace : currentUser.getWorkingPlaces() ) {
                        matched = x.getWorkingPlace().equals(workingPlace);
                    }
                    return matched;
                })
                .collect(Collectors.toList()).forEach(x -> petitionList.add(x.getPetition()));


        model.addAttribute("petitions", petitionList.stream().distinct().collect(Collectors.toList()));

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

        WorkingPlace workingPlace = currentUser.getEmployee().getWorkingPlace();
        //get index number from db and used to it create petition index number and petition number
        Petition petitionDb = (Petition) petitionService.getLastOne();
        String indexNumber =
                makeAutoGenerateNumberService.numberAutoGen(petitionDb.getIndexNumber()).toString();
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
                        minutePetitionFilesService.save(minutePetitionFile);
                    } else {
                        assert savedPetition != null;
                        minutePetitionFile = new MinutePetitionFiles(file.getOriginalFilename(),
                                                                     file.getContentType(),
                                                                     file.getBytes(),
                                                                     savedPetition.getPetitionNumber().concat("-" + LocalDateTime.now()),
                                                                     UUID.randomUUID().toString().concat(
                                                                             "minutePetition"));

                        minutePetitionFile.setMinutePetition(minutePetition1);
                        minutePetitionFilesService.save(minutePetitionFile);
                    }
                }
            }
        }

        // 3. Send email working place "CGE", "ACGE", "CE", "DCL", "DCLE", "ACE", "SE", "OIC"
        String[] codes = {"CGE", "ACGE", "CE", "DCL", "DCLE", "ACE", "SE", "OIC"};
        List< Role > roles = new ArrayList<>();
        for ( String roleName : codes ) {
            roles.add(roleService.findByRoleName(roleName));
        }

        HashSet< Employee > employees = new HashSet<>();
        for ( Role role : roles ) {
            userService.findByWorkingPlaceAndRoles(workingPlace, role)
                    .stream()
                    .filter(User::isEnabled)
                    .collect(Collectors.toList())
                    .forEach(user -> employees.add(user.getEmployee()));
        }


        if ( savedPetition != null ) {
            String petitionNumber = savedPetition.getPetitionNumber();
            String subject = "කරුණාකර මෙම පෙත්සම අවධානයට ගන්න: " + petitionNumber + " Please get attention this " +
                    "petition : " + petitionNumber + " இந்த மனுவை கவனத்தில் கொள்ளுங்கள்: " + petitionNumber;

            String message = "කරුණාකර මෙම පෙත්සම අවධානයට ගන්න: \t" + petitionNumber + "\nPlease get attention this " +
                    "petition: \t" +
                    " : " + petitionNumber + "\nஇந்த மனுவை கவனத்தில் கொள்ளுங்கள்: \t" + petitionNumber + "\n\n\n\n\n" +
                    "\n\n" +
                    "AGA අංශය (AGA Division, AGA பிரிவு) : \t" + savedPetition.getAgaDivision() + "\n" +
                    "ගම (Village, கிராமம்) : \t" + savedPetition.getVillage() + "\n" +
                    "විෂය (Subject, பொருள்) : \t" + savedPetition.getSubject() + "\n" +
                    "පෙත්සම් ප්\u200Dරමුඛතාවය (Petition Priority, மனு முன்னுரிமை) : \t" + savedPetition.getPetitionPriority().getPetitionPriority() + "\n" +
                    "අදාළ සුරාබදු ස්ථානය (Relevant Excise Station, தொடர்புடைய கலால் நிலையம்) : \t" + savedPetition.getWorkingPlace().getName() + "\n" +
                    "\n\n\n\n\n" +
                    "\n ස්තූතියි" +
                    "\n Thanks" +
                    "\n நன்றி" +
                    "\n\n\n" +
                    "\n" + savedPetition.getCreatedBy();

            for ( Employee employee : employees ) {
                emailService.sendEmail(employee.getOfficeEmail(), subject, message);
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
