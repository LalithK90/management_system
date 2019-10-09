package lk.imms.management_system.asset.employee.controller;


import lk.imms.management_system.asset.commonAsset.entity.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.entity.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.entity.Enum.Title;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/employee")
@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final UserService userService;
    private final DateTimeAgeService dateTimeAgeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, UserService userService, DateTimeAgeService dateTimeAgeService) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.dateTimeAgeService = dateTimeAgeService;
    }

    @RequestMapping
    public String employeePage(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/employee";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String employeeView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("employeeDetail", employeeService.findById(id));
        model.addAttribute("addStatus", false);
        return "employee/employee-detail";
    }

    // common things for employee add and update
    private void CommonThings(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEmployeeFrom(@PathVariable("id") Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        model.addAttribute("newEmployee", employeeService.findById(id).getNumber());
        model.addAttribute("addStatus", false);
        CommonThings(model);
        return "employee/addEmployee";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String employeeAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        CommonThings(model);
        model.addAttribute("employee", new Employee());
        return "employee/addEmployee";
    }


    @RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
    public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        /*
        *
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
        * */

        if (dateTimeAgeService.getAge(employee.getDateOfBirth()) < 18) {
            ObjectError error = new ObjectError("dateOfBirth", "Employee must be 18 old ");
            result.addError(error);
        }
        if (result.hasErrors()) {
            model.addAttribute("addStatus", true);
            if (employeeService.lastEmployee() != null) {
                model.addAttribute("lastEmployee", employeeService.lastEmployee().getNumber());
            }

            model.addAttribute("title", Title.values());
            model.addAttribute("gender", Gender.values());
            model.addAttribute("civilStatus", CivilStatus.values());
            model.addAttribute("employeeStatus", EmployeeStatus.values());
            model.addAttribute("designation", Designation.values());
            model.addAttribute("employee", employee);
            model.addAttribute("bloodGroup", BloodGroup.values());
            return "redirect:/employee/add";
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
        employeeService.persist(employee);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Employee employee) {
        model.addAttribute("employeeDetail", employeeService.search(employee));
        return "employee/employee-detail";
    }
}
