package lk.imms.management_system.asset.minute.controller;


import lk.imms.management_system.asset.employee.controller.EmployeeRestController;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.minute.entity.Enum.MinuteState;
import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.minute.service.MinutePetitionFilesService;
import lk.imms.management_system.asset.minute.service.MinutePetitionService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;

@Controller
@RequestMapping( "/minutePetition" )
public class MinutePetitionController {
    private final MinutePetitionService minutePetitionService;
    private final MinutePetitionFilesService minutePetitionFilesService;

    @Autowired
    public MinutePetitionController(MinutePetitionService minutePetitionService,
                                    MinutePetitionFilesService minutePetitionFilesService) {
        this.minutePetitionService = minutePetitionService;
        this.minutePetitionFilesService = minutePetitionFilesService;
    }

    @GetMapping
    public String getForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("designations", Designation.values());
        model.addAttribute("provinces", Province.values());
        model.addAttribute("minuteStates", MinuteState.values());
        model.addAttribute("petitionStateTypes", PetitionStateType.values());
        model.addAttribute("minutePetition", new MinutePetition());
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

    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String persist(@Valid @ModelAttribute("minutePetition") MinutePetition minutePetition, Model model, BindingResult result) {


        System.out.println(minutePetition.toString());
        return "redirect:/minutePetition";
    }
/*
    // Common things for an minutePetition add and update
    private String commonThings(Model model) {
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
        return "minutePetition/addEmployee";
    }

    //To get files from the database
    private void employeeFiles(MinutePetition minutePetition, Model model) {
        List< FileInfo > fileInfos = minutePetitionFilesService.findByMinutePetition(minutePetition)
                .stream()
                .map(MinutePetitionFiles -> {
                    String filename = MinutePetitionFiles.getName();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(MinutePetitionController.class, "downloadFile", MinutePetitionFiles
                            .getNewId())
                            .build()
                            .toString();
                    return new FileInfo(filename, MinutePetitionFiles.getCreatedAt(), url);
                })
                .collect(Collectors.toList());
        model.addAttribute("files", fileInfos);
    }

    //When scr called file will send to
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        MinutePetitionFiles file = minutePetitionFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    //Send all minutePetition data
    @RequestMapping
    public String employeePage(Model model) {
        model.addAttribute("employees", minutePetitionService.findAll());
        return "minutePetition/minutePetition";
    }

    //Send on minutePetition details
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String employeeView(@PathVariable( "id" ) Long id, Model model) {
        MinutePetition minutePetition = minutePetitionService.findById(id);
        model.addAttribute("employeeDetail", minutePetition);
        model.addAttribute("addStatus", false);
        employeeFiles(minutePetition, model);
        return "minutePetition/minutePetition-detail";
    }

    //Send minutePetition data edit
    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET )
    public String editEmployeeFrom(@PathVariable( "id" ) Long id, Model model) {
        MinutePetition minutePetition = minutePetitionService.findById(id);
        model.addAttribute("minutePetition", minutePetition);
        model.addAttribute("addStatus", false);
        employeeFiles(minutePetition, model);
        return commonThings(model);
    }

    //Send an minutePetition add from
    @RequestMapping( value = {"/add"}, method = RequestMethod.GET )
    public String employeeAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("minutePetition", new MinutePetition());
        return commonThings(model);
    }

    //MinutePetition add and update
    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String addEmployee(@Valid @ModelAttribute MinutePetition minutePetition, BindingResult result, Model model,
                              RedirectAttributes redirectAttributes) {

        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("minutePetition", minutePetition);
            return commonThings(model);
        }
        try {
            //todo->minutePetition controller logic to before save
            //todo->employeeFiles controller logic to before save

            System.out.println(minutePetition.getFiles());

            //first save minutePetition and
            minutePetitionService.persist(minutePetition);
            //save minutePetition images file
            List< MinutePetitionFiles > storedFile = new ArrayList<>();
            for ( MultipartFile file : minutePetition.getFiles() ) {
                MinutePetitionFiles minutePetitionFiles = minutePetitionFilesService.findByName(file
                .getOriginalFilename());
                if ( minutePetitionFiles != null ) {
                    // update new contents
                    minutePetitionFiles.setPic(file.getBytes());
                } else {
                    minutePetitionFiles = new MinutePetitionFiles(file.getOriginalFilename(),
                                                      file.getContentType(),
                                                      file.getBytes(),
                                                      minutePetition.getPetition().getIndexNumber().concat("-" +
                                                      LocalDateTime.now()),
                                                      UUID.randomUUID().toString().concat("minutePetition"));
                }
                minutePetitionFiles.setMinutePetition(minutePetition);
                storedFile.add(minutePetitionFiles);
            }

            // Save all Files to database
            minutePetitionFilesService.persist(storedFile);
            return "redirect:/minutePetition";

        } catch ( Exception e ) {
            ObjectError error = new ObjectError("minutePetition",
                                                "There is already in the system. <br>System message -->" + e.toString
                                                ());
            result.addError(error);
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("minutePetition", minutePetition);
            return commonThings(model);
        }
    }

    //If need to minutePetition {but not applicable for this }
    @RequestMapping( value = "/remove/{id}", method = RequestMethod.GET )
    public String removeEmployee(@PathVariable Long id) {
        minutePetitionService.delete(id);
        return "redirect:/minutePetition";
    }

    //To search minutePetition any giving minutePetition parameter
    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public String search(Model model, MinutePetition minutePetition) {
        model.addAttribute("employeeDetail", minutePetitionService.search(minutePetition));
        return "minutePetition/minutePetition-detail";
    }

    */

}
