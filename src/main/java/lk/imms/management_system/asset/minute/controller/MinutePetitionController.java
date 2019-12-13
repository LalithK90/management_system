package lk.imms.management_system.asset.minute.controller;


import lk.imms.management_system.asset.employee.controller.EmployeeRestController;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.minute.entity.Enum.MinuteState;
import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.minute.entity.MinutePetitionFiles;
import lk.imms.management_system.asset.minute.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minute.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping( "/minutePetition" )
public class MinutePetitionController {
    private final MinutePetitionService minutePetitionService;
    private final MinutePetitionFilesService minutePetitionFilesService;
    private final PetitionService petitionService;
    private final PetitionStateService petitionStateService;

    @Autowired
    public MinutePetitionController(MinutePetitionService minutePetitionService,
                                    MinutePetitionFilesService minutePetitionFilesService,
                                    PetitionService petitionService, PetitionStateService petitionStateService) {
        this.minutePetitionService = minutePetitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionService = petitionService;
        this.petitionStateService = petitionStateService;
    }

    private String commonCode(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("designations", Designation.values());
        model.addAttribute("provinces", Province.values());
        model.addAttribute("minuteStates", MinuteState.values());
        model.addAttribute("petitionStateTypes", PetitionStateType.values());
        model.addAttribute("districtUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                .build()
                .toString());
        model.addAttribute("stationUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                .build()
                .toString());
        Object[] arg = {"designation", "id"};
        model.addAttribute("employeeUrl", MvcUriComponentsBuilder
                .fromMethodName(EmployeeRestController.class, "getEmployeeByWorkingPlace", arg)
                .build()
                .toString());
        return "minutePetition/addMinutePetition";
    }

    @GetMapping( "/{petitionId}" )
    public String getForm(@PathVariable( "petitionId" ) Long id, Model model) {
        MinutePetition minutePetition = new MinutePetition();
        minutePetition.setPetition(petitionService.findById(id));
        model.addAttribute("minutePetition", minutePetition);
        return commonCode(model);
    }

    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String persist(@Valid @ModelAttribute( "minutePetition" ) MinutePetition minutePetition, Model model,
                          BindingResult result) throws IOException {
        if ( result.hasErrors() ) {
            model.addAttribute("minutePetition", minutePetition);
            return commonCode(model);
        }
        MinutePetition minutePetition1 = minutePetitionService.persist(minutePetition);

        //petition state change
        PetitionState petitionState = petitionStateService.findByPetition(minutePetition1.getPetition());
        petitionState.setPetitionStateType(minutePetition.getPetitionStateType());
        petitionStateService.persist(petitionState);

        // Minute Petition Files
        List< MinutePetitionFiles > storedFile = new ArrayList<>();
        //if there is nothing to save files
        if ( !minutePetition.getFiles().isEmpty() ) {
            for ( MultipartFile file : minutePetition.getFiles() ) {
                MinutePetitionFiles minutePetitionFile =
                        minutePetitionFilesService.findByName(file.getOriginalFilename());
                if ( minutePetitionFile != null ) {
                    // update new contents
                    minutePetitionFile.setPic(file.getBytes());
                } else {
                    minutePetitionFile = new MinutePetitionFiles(file.getOriginalFilename(),
                                                                 file.getContentType(),
                                                                 file.getBytes(),
                                                                 minutePetition1.getPetition().getPetitionNumber().concat("-" + LocalDateTime.now()),
                                                                 UUID.randomUUID().toString().concat(
                                                                         "minutePetition"));
                }
                minutePetitionFile.setMinutePetition(minutePetition1);
                storedFile.add(minutePetitionFile);
            }
            minutePetitionFilesService.persist(storedFile);
        }

        return "redirect:/petition";
    }


}
