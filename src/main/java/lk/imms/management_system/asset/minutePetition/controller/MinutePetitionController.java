package lk.imms.management_system.asset.minutePetition.controller;


import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.minutePetition.entity.Enum.MinuteState;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetitionFiles;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final CommonCodeService commonCodeService;

    @Autowired
    public MinutePetitionController(MinutePetitionService minutePetitionService,
                                    MinutePetitionFilesService minutePetitionFilesService,
                                    PetitionService petitionService, PetitionStateService petitionStateService,
                                    CommonCodeService commonCodeService) {
        this.minutePetitionService = minutePetitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionService = petitionService;
        this.petitionStateService = petitionStateService;
        this.commonCodeService = commonCodeService;
    }

    private String commonCode(Model model) {
        model.addAttribute("minuteStates", MinuteState.values());
        model.addAttribute("petitionStateTypes", PetitionStateType.values());
        commonCodeService.commonUrlBuilder(model);
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
