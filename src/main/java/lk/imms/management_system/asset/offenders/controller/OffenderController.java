package lk.imms.management_system.asset.offenders.controller;

import lk.imms.management_system.asset.commonAsset.entity.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Title;
import lk.imms.management_system.asset.commonAsset.entity.FileInfo;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.offenders.entity.Enum.GuardianType;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.asset.offenders.entity.OffenderCallingName;
import lk.imms.management_system.asset.offenders.entity.OffenderFiles;
import lk.imms.management_system.asset.offenders.service.OffenderFilesService;
import lk.imms.management_system.asset.offenders.service.OffenderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/offender" )
public class OffenderController {
    private final OffenderService offenderService;
    private final OffenderFilesService offenderFilesService;

    public OffenderController(OffenderService offenderService, OffenderFilesService offenderFilesService) {
        this.offenderService = offenderService;
        this.offenderFilesService = offenderFilesService;
    }

    // Common things for an offender add and update
    private String commonThings(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
        model.addAttribute("guardianTypes", GuardianType.values());
        return "offender/addOffender";
    }

    //To get files from the database
    private void offenderFiles(Offender offender, Model model) {
        List< FileInfo > fileInfos = offenderFilesService.findByOffender(offender)
                .stream()
                .map(OffenderFiles -> {
                    String filename = OffenderFiles.getName();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(OffenderController.class, "downloadFile", OffenderFiles.getNewId())
                            .build()
                            .toString();
                    return new FileInfo(filename, OffenderFiles.getCreatedAt(), url);
                })
                .collect(Collectors.toList());
        model.addAttribute("files", fileInfos);
    }

    //When called file will send to 
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        OffenderFiles file = offenderFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    //Send all offender data
    @RequestMapping
    public String offenderPage(Model model) {
        model.addAttribute("offenders", offenderService.findAll());
        return "offender/offender";
    }

    //Send on offender details
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String offenderView(@PathVariable( "id" ) Long id, Model model) {
        Offender offender = offenderService.findById(id);
        model.addAttribute("offenderDetail", offender);
        model.addAttribute("addStatus", false);
        offenderFiles(offender, model);
        return "offender/offender-detail";
    }

    //Send offender data edit
    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET )
    public String editOffenderFrom(@PathVariable( "id" ) Long id, Model model) {
        Offender offender = offenderService.findById(id);
        model.addAttribute("offender", offender);
        model.addAttribute("addStatus", false);
        offenderFiles(offender, model);
        return commonThings(model);
    }

    //Send an offender add from
    @RequestMapping( value = {"/add"}, method = RequestMethod.GET )
    public String offenderAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("offender", new Offender());
        /*model.addAttribute("offenderCallingName", new OffenderCallingName());*/
        return commonThings(model);
    }

    //Offender add and update
    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String addOffender(@Valid @ModelAttribute("offender") Offender offender, BindingResult result, Model model,
                              RedirectAttributes redirectAttributes) {
        System.out.println(offender.toString());
/*
        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("offender", offender);
            return  commonThings(model);
        }
        try {
            //Todo->offender controller logic too before save
            //Todo->offenderFiles controller logic too before save

            System.out.println(offender.getFiles());

            //First save offender and
            offenderService.persist(offender);
            //Save offender images file
            List< OffenderFiles > storedFile = new ArrayList<>();
            for ( MultipartFile file : offender.getFiles() ) {
                OffenderFiles offenderFiles = offenderFilesService.findByName(file.getOriginalFilename());
                if ( offenderFiles != null ) {
                    // update new contents
                    offenderFiles.setPic(file.getBytes());
                } else {
                    offenderFiles = new OffenderFiles(file.getOriginalFilename(),
                                                      file.getContentType(),
                                                      file.getBytes(),
                                                      offender.getNic().concat("-" + LocalDateTime.now()),
                                                      UUID.randomUUID().toString().concat("offender"));
                }
                offenderFiles.setOffender(offender);
                storedFile.add(offenderFiles);
            }

            // Save all Files to database
            offenderFilesService.persist(storedFile);
            return "redirect:/offender";

        } catch ( Exception e ) {
            ObjectError error = new ObjectError("offender",
                                                "There is already in the system. <br>System message -->" + e.toString
                                                ());
            result.addError(error);
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("offender", offender);
            return  commonThings(model);
        }*/
try {
    offenderService.persist(offender);
}catch ( Exception e ){
    System.out.println(e.toString());
}

        return "redirect:/offender/add";
    }

    //If need to offender {but not applicable for this }
    @RequestMapping( value = "/remove/{id}", method = RequestMethod.GET )
    public String removeOffender(@PathVariable Long id) {
        offenderService.delete(id);
        return "redirect:/offender";
    }

    //To search offender any giving offender parameter
    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public String search(Model model, Offender offender) {
        model.addAttribute("offenderDetail", offenderService.search(offender));
        return "offender/offender-detail";
    }
}
