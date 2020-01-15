package lk.imms.management_system.asset.employee.controller;


import lk.imms.management_system.asset.commonAsset.entity.FileInfo;
import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.EmployeeFiles;
import lk.imms.management_system.asset.employee.entity.EmployeeWorkingPlaceHistory;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.employee.entity.Enum.WorkingPlaceChangeReason;
import lk.imms.management_system.asset.employee.service.EmployeeFilesService;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.employee.service.EmployeeWorkingPlaceHistoryService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
import lk.imms.management_system.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequestMapping( "/employee" )
@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeWorkingPlaceHistoryService employeeWorkingPlaceHistoryService;
    private final EmployeeFilesService employeeFilesService;
    private final DateTimeAgeService dateTimeAgeService;
    private final WorkingPlaceService workingPlaceService;
    private final CommonCodeService commonCodeService;
    private final UserService userService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              EmployeeWorkingPlaceHistoryService employeeWorkingPlaceHistoryService,
                              EmployeeFilesService employeeFilesService, DateTimeAgeService dateTimeAgeService,
                              WorkingPlaceService workingPlaceService, CommonCodeService commonCodeService,
                              UserService userService) {
        this.employeeService = employeeService;
        this.employeeWorkingPlaceHistoryService = employeeWorkingPlaceHistoryService;
        this.employeeFilesService = employeeFilesService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.workingPlaceService = workingPlaceService;
        this.commonCodeService = commonCodeService;
        this.userService = userService;
    }
//----> Employee details management - start <----//

    // Common things for an employee add and update
    private String commonThings(Model model) {
       commonCodeService.commonEmployeeAndOffender(model);
        model.addAttribute("workingPlaces", workingPlaceService.findAll());
        return "employee/addEmployee";
    }

    //When scr called file will send to
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        EmployeeFiles file = employeeFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    //Send all employee data
    @RequestMapping
    public String employeePage(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/employee";
    }

    //Send on employee details
    @GetMapping( value = "/{id}")
    public String employeeView(@PathVariable( "id" ) Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("addStatus", false);
        model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
        return "employee/employee-detail";
    }

    //Send employee data edit
    @GetMapping( value = "/edit/{id}" )
    public String editEmployeeForm(@PathVariable( "id" ) Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("newEmployee", employee.getPayRoleNumber());
        model.addAttribute("addStatus", false);
        model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
        return commonThings(model);
    }

    //Send an employee add form
    @GetMapping( value = {"/add"} )
    public String employeeAddForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("employee", new Employee());
        return commonThings(model);
    }

    //Employee add and update
    @PostMapping( value = {"/add", "/update"} )
    public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model,
                              RedirectAttributes redirectAttributes) {

        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("employee", employee);
            return commonThings(model);
        }
        try {
            //if employee state is not working he or she cannot access to the system
            if  (!employee.getEmployeeStatus().equals(EmployeeStatus.WORKING)){
                User user = userService.findUserByEmployee(employeeService.findByNic(employee.getNic()));
                //if employee not a user
                if ( user != null ) {
                    user.setEnabled(false);
                    userService.persist(user);
                }
            }
            //save employee images file
            List< EmployeeFiles > storedFile = new ArrayList<>();
            for ( MultipartFile file : employee.getFiles() ) {
                EmployeeFiles employeeFiles = employeeFilesService.findByName(file.getOriginalFilename());
                if ( employeeFiles != null ) {
                    // update new contents
                    employeeFiles.setPic(file.getBytes());
                } else {
                    employeeFiles = new EmployeeFiles(file.getOriginalFilename(),
                                                      file.getContentType(),
                                                      file.getBytes(),
                                                      employee.getNic().concat("-" + LocalDateTime.now()),
                                                      UUID.randomUUID().toString().concat("employee"));
                }
                employeeFiles.setEmployee(employee);
                storedFile.add(employeeFiles);
            }

            // Save all Files to database
            employeeFilesService.persist(storedFile);
            employee.setMobileOne(commonCodeService.commonMobileNumberLengthValidator(employee.getMobileOne()));
            employee.setMobileTwo(commonCodeService.commonMobileNumberLengthValidator(employee.getMobileTwo()));
            employee.setLand(commonCodeService.commonMobileNumberLengthValidator(employee.getLand()));
            //after save employee files and save employee
            employeeService.persist(employee);
            return "redirect:/employee";

        } catch ( Exception e ) {
            ObjectError error = new ObjectError("employee",
                                                "There is already in the system. <br>System message -->" + e.toString());
            result.addError(error);
            model.addAttribute("addStatus", true);
            redirectAttributes.addFlashAttribute("employee", employee);
            return commonThings(model);
        }
    }

    //If need to employee {but not applicable for this }
    @GetMapping( value = "/remove/{id}")
    public String removeEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }

    //To search employee any giving employee parameter
    @GetMapping( value = "/search" )
    public String search(Model model, Employee employee) {
        model.addAttribute("employeeDetail", employeeService.search(employee));
        return "employee/employee-detail";
    }

//----> Employee details management - end <----//
    //````````````````````````````````````````````````````````````````````````````//
//----> EmployeeWorkingPlace - details management - start <----//

    //Send form to add working place before find employee
    @GetMapping( value = "/workingPlace")
    public String addEmployeeWorkingPlaceForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("employeeDetailShow", false);
        return "employeeWorkingPlace/addEmployeeWorkingPlace";
    }

    //Send a searched employee to add working place
    @PostMapping( value = "/workingPlace")
    public String addWorkingPlaceEmployeeDetails(@ModelAttribute( "employee" ) Employee employee, Model model) {

        List< Employee > employees = employeeService.search(employee);
        if ( employees.size() == 1 ) {
            model.addAttribute("employeeDetailShow", true);
            model.addAttribute("employeeNotFoundShow", false);
            model.addAttribute("employeeDetail", employees.get(0));
            model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee).get(0));
            model.addAttribute("employeeWorkingPlaceHistoryObject", new EmployeeWorkingPlaceHistory());
            model.addAttribute("workingPlaceChangeReason", WorkingPlaceChangeReason.values());
            model.addAttribute("province", Province.values());
            model.addAttribute("districtUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                    .build()
                    .toString());
            model.addAttribute("stationUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                    .build()
                    .toString());
            return "employeeWorkingPlace/addEmployeeWorkingPlace";
        }
        model.addAttribute("employee", new Employee());
        model.addAttribute("employeeDetailShow", false);
        model.addAttribute("employeeNotFoundShow", true);
        model.addAttribute("employeeNotFound", "There is not employee in the system according to the provided details" +
                " \n Could you please search again !!");

        return "employeeWorkingPlace/addEmployeeWorkingPlace";
    }

    @PostMapping( value = "/workingPlace/add")
    public String addWorkingPlaceEmployee(@ModelAttribute( "employeeWorkingPlaceHistory" ) EmployeeWorkingPlaceHistory employeeWorkingPlaceHistory, Model model) {
        System.out.println(employeeWorkingPlaceHistory.toString());
        // -> need to write validation before the save working place
        //before saving set employee current working palace
        WorkingPlace workingPlace = employeeWorkingPlaceHistory.getWorkingPlace();

        employeeWorkingPlaceHistory.setWorkingPlace(employeeWorkingPlaceHistory.getEmployee().getWorkingPlace());
        employeeWorkingPlaceHistory.getEmployee().setWorkingPlace(workingPlace);

        employeeWorkingPlaceHistory.setWorkingDuration(dateTimeAgeService.dateDifference(employeeWorkingPlaceHistory.getFrom_place(), employeeWorkingPlaceHistory.getTo_place()));
        employeeWorkingPlaceHistoryService.persist(employeeWorkingPlaceHistory);
        return "redirect:/employee";
    }

//----> EmployeeWorkingPlace - details management - end <----//

}
/*
 try {
            List< FileModel > storedFile = new ArrayList< FileModel >();

            for ( MultipartFile file : files ) {
                FileModel fileModel = fileRepository.findByName(file.getOriginalFilename());
                if ( fileModel != null ) {
                    // update new contents
                    fileModel.setPic(file.getBytes());
                } else {
                    fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                }

                fileNames.add(file.getOriginalFilename());
                storedFile.add(fileModel);
            }

            // Save all Files to database
            fileRepository.saveAll(storedFile);

            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
        } catch ( Exception e ) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }

* */

/*
 public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model,
 RedirectAttributes redirectAttributes) {

        * String newEmployeeNumber = "";
        String input;
        if (employeeService.lastEmployee() != null) {
            input = employeeService.lastEmployee().getNumber();
            int employeeNumber = Integer.valueOf(input.replaceAll("[^0-9]+", "")).intValue() + 1;

            if ((employeeNumber < 10) && (employeeNumber > 0)) {
                newEmployeeNumber = "KL000" + employeeNumber;
            }
            if ((employeeNumber < 100) && (employeeNumber > 10)) {
                newEmployeeNumber = "KL00" + employeeNumber;
            }
            if ((employeeNumber < 1000) && (employeeNumber > 100)) {
                newEmployeeNumber = "KL0" + employeeNumber;
            }
            if (employeeNumber > 10000) {
                newEmployeeNumber = "KL" + employeeNumber;
            }
        } else {
            newEmployeeNumber = "KL0001";
            input = "KL0000";
        }


        if (dateTimeAgeService.getAge(employee.getDateOfBirth()) < 18) {
            ObjectError error = new ObjectError("dateOfBirth", "Employee must be 18 old ");
            result.addError(error);
        }
        if (result.hasErrors()) {
                System.out.println("i m here");
                model.addAttribute("addStatus", true);
            if (employeeService.lastEmployee() != null) {
                model.addAttribute("lastEmployee", employeeService.lastEmployee().getPayRoleNumber());
            }


                model.addAttribute("addStatus", true);
                CommonThings(model);
                redirectAttributes.addFlashAttribute("employee", employee);
                redirectAttributes.addFlashAttribute("files", employee.getFiles());
                return "employee/addEmployee";
                }

      if (employeeService.isEmployeePresent(employee)) {
            System.out.println("already on ");
            User user = userService.findById(userService.findByEmployeeId(employee.getId()));
            if (employee.getEmployeeStatus() != EmployeeStatus.WORKING) {
                user.setEnabled(false);
                employeeService.persist(employee);
            }
            System.out.println("update working");
            user.setEnabled(true);
            employeeService.persist(employee);
            return "redirect:/employee";
        }
        if (employee.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Successfully Add but Email was not sent.");
            redirectAttributes.addFlashAttribute("alertStatus", false);

            employeeService.persist(employee);
        }


        System.out.println("save no id");
                System.out.println("Employee come "+employee.toString());
                //employeeService.persist(employee);
                return "redirect:/employee";
                }

 */