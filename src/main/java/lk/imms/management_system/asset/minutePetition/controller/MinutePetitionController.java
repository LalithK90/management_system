package lk.imms.management_system.asset.minutePetition.controller;


import lk.imms.management_system.asset.commonAsset.service.CommonService;
import lk.imms.management_system.asset.minutePetition.entity.Enum.MinuteState;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetitionFiles;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minutePetition.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import lk.imms.management_system.asset.petition.service.PetitionService;
import lk.imms.management_system.asset.petition.service.PetitionStateService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CommonService commonService;
    private final UserService userService;

    @Autowired
    public MinutePetitionController(MinutePetitionService minutePetitionService,
                                    MinutePetitionFilesService minutePetitionFilesService,
                                    PetitionService petitionService, PetitionStateService petitionStateService,
                                    CommonService commonService, UserService userService) {
        this.minutePetitionService = minutePetitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
        this.petitionService = petitionService;
        this.petitionStateService = petitionStateService;
        this.commonService = commonService;
        this.userService = userService;
    }

    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        MinutePetitionFiles file = minutePetitionFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    private String commonCode(Model model) {
        model.addAttribute("minuteStates", MinuteState.values());
        model.addAttribute("petitionStateTypes", PetitionStateType.values());
        commonService.commonUrlBuilder(model);
        return "minutePetition/addMinutePetition";
    }

    @GetMapping( "/{petitionId}" )
    public String getForm(@PathVariable( "petitionId" ) Long id, Model model) {
        MinutePetition minutePetition = new MinutePetition();
        minutePetition.setPetition(petitionService.findById(id));
        model.addAttribute("minutePetition", minutePetition);
        return commonCode(model);
    }

    @PostMapping( value = {"/add", "/update"} )
    public String persist(@Valid @ModelAttribute( "minutePetition" ) MinutePetition minutePetition, Model model,
                          BindingResult result) throws IOException {
        if ( result.hasErrors() ) {
            model.addAttribute("minutePetition", minutePetition);
            return commonCode(model);
        }
        //cannot be working place null on minute petition so recieved minute petition is null current login user
        // working place set as working place
        if ( minutePetition.getEmployee().getId() == null ) {
            minutePetition.setWorkingPlace(
                    userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()))
                            .getEmployee().getWorkingPlace()  );
        }
        MinutePetition minutePetition1 = minutePetitionService.persist(minutePetition);

        //petition state change
        PetitionState petitionState = new PetitionState();
        petitionState.setPetition(minutePetition.getPetition());
        petitionState.setPetitionStateType(minutePetition.getPetitionStateType());
        petitionStateService.persist(petitionState);

        // Minute Petition Files
        List< MinutePetitionFiles > storedFile = new ArrayList<>();
        //if there is nothing to save files
        if ( !minutePetition.getFiles().isEmpty() && minutePetition.getFiles().size() != 0 && minutePetition.getFiles().get(0).getOriginalFilename() != null ) {
            for ( MultipartFile file : minutePetition.getFiles() ) {
                if ( file.getOriginalFilename() == null && file.getContentType() == "application/octet-stream" ) {
                    continue;
                }
                MinutePetitionFiles minutePetitionFile =
                        minutePetitionFilesService.findByName(file.getOriginalFilename());
                if ( minutePetitionFile != null ) {
                    // update new contents
                    minutePetitionFile.setPic(file.getBytes());
                } else {
                    minutePetitionFile = new MinutePetitionFiles(file.getOriginalFilename(),
                                                                 file.getContentType(),
                                                                 file.getBytes(),
                                                                 minutePetition.getPetition().getPetitionNumber().concat("-" + LocalDateTime.now()),
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
