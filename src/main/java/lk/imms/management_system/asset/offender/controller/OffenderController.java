package lk.imms.management_system.asset.offender.controller;

import lk.imms.management_system.asset.OffednerGuardian.entity.Enum.GuardianType;
import lk.imms.management_system.asset.OffednerGuardian.entity.Guardian;
import lk.imms.management_system.asset.OffednerGuardian.service.GuardianService;
import lk.imms.management_system.asset.commonAsset.service.CommonService;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.entity.OffenderCallingName;
import lk.imms.management_system.asset.offender.entity.OffenderFiles;
import lk.imms.management_system.asset.offender.service.OffenderFilesService;
import lk.imms.management_system.asset.offender.service.OffenderService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.UserService;
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
    private final UserService userService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final ContraveneService contraveneService;
    private final GuardianService guardianService;
    private final CommonService commonService;

    @Autowired
    public OffenderController(OffenderService offenderService, OffenderFilesService offenderFilesService,
                              UserService userService, MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                              ContraveneService contraveneService, GuardianService guardianService,
                              CommonService commonService) {
        this.offenderService = offenderService;
        this.offenderFilesService = offenderFilesService;
        this.userService = userService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.contraveneService = contraveneService;
        this.guardianService = guardianService;
        this.commonService = commonService;
    }

    //When called file will send to offender image
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
    @GetMapping( value = "/{id}")
    public String offenderView(@PathVariable( "id" ) Long id, Model model) {
        Offender offender = offenderService.findById(id);
        offender.setFileInfos(offenderFilesService.offenderFileDownloadLinks(offender));
        model.addAttribute("offender", offender);
        model.addAttribute("addStatus", false);
        //model.addAttribute("files", );

        return "offender/offender-detail";
    }

    //Send offender data edit
    @GetMapping( value = "/edit/{id}" )
    public String editOffenderFrom(@PathVariable( "id" ) Long id, Model model) {
        Offender offender = offenderService.findById(id);
        model.addAttribute("offender", offender);
        model.addAttribute("addStatus", false);
        model.addAttribute("files", offenderFilesService.offenderFileDownloadLinks(offender));
        commonService.commonEmployeeAndOffender(model);

        model.addAttribute("guardianTypes", GuardianType.values());
        return "offender/addOffender";
    }

    //Send an offender add from
    @GetMapping( value = {"/add"})
    public String offenderAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("offender", new Offender());
        commonService.commonEmployeeAndOffender(model);
        model.addAttribute("guardianTypes", GuardianType.values());
        return "offender/addOffender";
    }

    //Offender add and update
    @PostMapping( value = {"/add", "/update"})
    public String addOffender(@Valid @ModelAttribute( "offender" ) Offender offender, BindingResult result,
                              Model model) {
        //get current login user
        User currentUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        // is there
        if ( offender.getId() != null ) {
            offender.setId(offender.getId());
        } else {
            //last offender Id offender registration number
            String regNumber;

            if ( offenderService.getLastOne() == null ) {
                regNumber = makeAutoGenerateNumberService.numberAutoGen(null).toString();
            } else {
                regNumber =
                        makeAutoGenerateNumberService.numberAutoGen(offenderService.getLastOne().getOffenderRegisterNumber()).toString();
            }
            offender.setOffenderRegisterNumber(regNumber + "/" + currentUser.getWorkingPlaces().get(0).getCode() +
                                                       "/OF");
        }

        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            commonService.commonEmployeeAndOffender(model);
            model.addAttribute("guardianTypes", GuardianType.values());
            model.addAttribute("offender", offender);
            return "offender/addOffender";
        }

//offender calling is not
        if ( offender.getOffenderCallingNames() != null ) {
            List< OffenderCallingName > offenderCallingNames = null;
            //offender calling name set
            offenderCallingNames = new ArrayList<>();
            for ( OffenderCallingName offenderCallingName : offender.getOffenderCallingNames() ) {
                offenderCallingName.setOffender(offender);
                offenderCallingNames.add(offenderCallingName);
            }
            offender.setOffenderCallingNames(offenderCallingNames);
        }
//offender guardian is not
        if ( offender.getGuardians() != null ) {
            //guardian details' offender set
            List< Guardian > guardians = new ArrayList<>();
            for ( Guardian guardian : offender.getGuardians() ) {
                //if this guardian already in system it was added to this offender
                if ( guardianService.findByNic(guardian.getNic()) != null ) {
                    guardian = guardianService.findByNic(guardian.getNic());
                } /*else {
                    guardian.setOffenders(offender);
                }*/
                guardians.add(guardian);
            }
            offender.setGuardians(guardians);
        }

        // System.out.println("after set guardian and calling name " + offender.toString());
        try {
            offender.setMobileOne(commonService.commonMobileNumberLengthValidator(offender.getMobileOne()));
            offender.setMobileTwo(commonService.commonMobileNumberLengthValidator(offender.getMobileTwo()));
            offender.setLand(commonService.commonMobileNumberLengthValidator(offender.getLand()));
            //after file save offender and
            Offender offender1 = offenderService.persist(offender);
            //offender file is not
            if ( offender.getFiles() != null ) {
                for ( MultipartFile file : offender.getFiles() ) {
                    if ( file.getOriginalFilename() == null && file.getContentType().equals("application/octet-stream") ) {
                        continue;
                    }
                    if ( file.getOriginalFilename() != null ) {
                        OffenderFiles offenderFiles = offenderFilesService.findByNameAndOffender(file.getOriginalFilename(),offender1);
                        if ( offenderFiles != null ) {
                            // update new contents
                            offenderFiles.setPic(file.getBytes());
                        } else {
                            offenderFiles = new OffenderFiles(file.getOriginalFilename(),
                                                              file.getContentType(),
                                                              file.getBytes(),
                                                              offender1.getNic().concat("/" + LocalDateTime.now()),
                                                              UUID.randomUUID().toString().concat("offender"));
                        }
                        offenderFiles.setOffender(offender1);
                        //storedFile.add(offenderFiles);
                        offenderFilesService.save(offenderFiles);
                    }
                }
            }
            return "redirect:/offender";

        } catch ( Exception e ) {
            ObjectError error = new ObjectError("offender",
                                                "There is already in the system. <br>System message -->" + e.toString
                                                        ());
            result.addError(error);
            if ( offender.getId() == null ) {
                model.addAttribute("addStatus", true);
            } else {
                model.addAttribute("addStatus", false);
            }
            commonService.commonEmployeeAndOffender(model);
            model.addAttribute("guardianTypes", GuardianType.values());
            model.addAttribute("offender", offender);
            return "offender/addOffender";
        }
    }

    //If need to offender {but not applicable for this }
    @GetMapping( value = "/remove/{id}" )
    public String removeOffender(@PathVariable Long id) {
       // offenderService.delete(id);
        return "redirect:/offender";
    }

    //To search form page to visible
    @GetMapping( "/search" )
    public String searchForm(Model model) {
        model.addAttribute("offender", new Offender());
        commonService.commonEmployeeAndOffender(model);
        model.addAttribute("guardianTypes", GuardianType.values());
        model.addAttribute("contravenes", contraveneService.findAll());
        return "offender/offenderSearch";
    }

    //To search offender any giving offender parameter
    @PostMapping( value = "/search" )
    public String search(@ModelAttribute( "offender" ) Offender offender, Model model) {

        //final stage before send data to frontend
        // if there is any duplicate remove from the list
        List< Offender > offenderList = offenderService.search(offender).stream()
                .distinct()
                .collect(Collectors.toList());

        if ( offenderList.size() == 1 ) {
            model.addAttribute("offender", offenderList.get(0));
            model.addAttribute("addStatus", false);
            model.addAttribute("files",
                               offenderFilesService.offenderFileDownloadLinks(offenderService.search(offender).get(0)));
            return "offender/offender-detail";
        } else {
            for ( Offender offender1 : offenderList ) {
                offender1.setFileInfos(offenderFilesService.offenderFileDownloadLinks(offender1));
                offenderList.add(offender1);
            }
            model.addAttribute("offenders", offenderList);
            return "offender/offender";
        }
    }

}
