package lk.imms.management_system.asset.employee.controller;


import lk.imms.management_system.asset.commonAsset.entity.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Title;
import lk.imms.management_system.asset.commonAsset.entity.FileInfo;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.EmployeeFiles;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.employee.service.EmployeeFilesService;
import lk.imms.management_system.asset.employee.service.EmployeeService;
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
    private final EmployeeFilesService employeeFilesService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeFilesService employeeFilesService) {
        this.employeeService = employeeService;
        this.employeeFilesService = employeeFilesService;
    }


    // common things for employee add and update
    private void commonThings(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
    }

    //to get files from the database
    public void employeeFiles(Employee employee, Model model) {
        List< FileInfo > fileInfos = employeeFilesService.findByEmployee(employee)
                .stream()
                .map(EmployeeFiles -> {
                    String filename = EmployeeFiles.getName();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(EmployeeController.class, "downloadFile", EmployeeFiles.getNewId())
                            .build()
                            .toString();
                    return new FileInfo(filename, EmployeeFiles.getCreatedAt(), url);
                })
                .collect(Collectors.toList());
        model.addAttribute("files", fileInfos);
    }

    //when scr called file will send to
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        EmployeeFiles file = employeeFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    @RequestMapping
    public String employeePage(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/employee";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String employeeView(@PathVariable( "id" ) Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("addStatus", false);
        employeeFiles(employee, model);
        return "employee/employee-detail";
    }


    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET )
    public String editEmployeeFrom(@PathVariable( "id" ) Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("newEmployee", employee.getPayRoleNumber());
        model.addAttribute("addStatus", false);
        commonThings(model);
        employeeFiles(employee, model);
        return "employee/addEmployee";
    }

    @RequestMapping( value = {"/add"}, method = RequestMethod.GET )
    public String employeeAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        commonThings(model);
        model.addAttribute("employee", new Employee());
        return "employee/addEmployee";
    }


    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model,
                              RedirectAttributes redirectAttributes) {
        System.out.println("employee " + employee.toString());

        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            commonThings(model);
            redirectAttributes.addFlashAttribute("employee", employee);
            return "employee/addEmployee";
        }
        try {
            //todo->employee controller logic to before save
            //todo->employeeFiles controller logic to before save

            //first save employee and
            employeeService.persist(employee);
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
            return "redirect:/employee";

        } catch ( Exception e ) {
            ObjectError error = new ObjectError("employee",
                                                "There is already in the system. <br>System message -->" + e.toString());
            result.addError(error);
            model.addAttribute("addStatus", true);
            commonThings(model);
            redirectAttributes.addFlashAttribute("employee", employee);
        }
        return "employee/addEmployee";
    }

    @RequestMapping( value = "/remove/{id}", method = RequestMethod.GET )
    public String removeEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }

    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public String search(Model model, Employee employee) {
        model.addAttribute("employeeDetail", employeeService.search(employee));
        return "employee/employee-detail";
    }

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