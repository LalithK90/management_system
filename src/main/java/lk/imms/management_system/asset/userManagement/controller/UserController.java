package lk.imms.management_system.asset.userManagement.controller;


import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping( "/user" )
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final EmployeeService employeeService;
    private final DateTimeAgeService dateTimeAgeService;

    @Autowired
    public UserController(UserService userService, EmployeeService employeeService,
                          DateTimeAgeService dateTimeAgeService, RoleService roleService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.roleService = roleService;
    }

    @RequestMapping
    public String userPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/user";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String userView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("userDetail", userService.findById(id));
        return "user/user-detail";
    }

    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET )
    public String editUserFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("addStatus", false);
        model.addAttribute("employeeDetailShow", true);
        model.addAttribute("employeeNotFoundShow", false);
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("province", Province.values());
        model.addAttribute("districtUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                .build()
                .toString());
        model.addAttribute("stationUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                .build()
                .toString());
        return "user/addUser";
    }

    @RequestMapping( value = "/add", method = RequestMethod.GET )
    public String userAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("employeeDetailShow", false);
        model.addAttribute("employee", new Employee());
        return "user/addUser";
    }

    //Send a searched employee to add working place
    @RequestMapping( value = "/workingPlace", method = RequestMethod.POST )
    public String addUserEmployeeDetails(@ModelAttribute( "employee" ) Employee employee, Model model) {

        List< Employee > employees = employeeService.search(employee);

        if ( employees.size() == 1 ) {
            model.addAttribute("user", new User());
            model.addAttribute("employee", employees.get(0));
            model.addAttribute("addStatus", true);
            model.addAttribute("employeeDetailShow", true);
            model.addAttribute("employeeNotFoundShow", false);
            model.addAttribute("roleList", roleService.findAll());
            model.addAttribute("province", Province.values());
            model.addAttribute("districtUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                    .build()
                    .toString());
            model.addAttribute("stationUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                    .build()
                    .toString());
            return "user/addUser";
        }
        model.addAttribute("addStatus", true);
        model.addAttribute("employee", new Employee());
        model.addAttribute("employeeDetailShow", false);
        model.addAttribute("employeeNotFoundShow", true);
        model.addAttribute("employeeNotFound", "There is not employee in the system according to the provided details" +
                " \n Could you please search again !!");

        return "user/addUser";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping( value = {"/add", "/update"}, method = RequestMethod.POST )
    public String addUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {

//todo -> configu more tings
        if ( userService.findUserByEmployee(user.getEmployee()) != null ) {
            ObjectError error = new ObjectError("employee", "This employee already defined as a user");
            result.addError(error);
        }
        if ( user.getId() != null ){
            User dbUser = userService.findById(user.getId());
            dbUser.setEnabled(true);
            dbUser.setPassword(user.getPassword());
            dbUser.setEmployee(user.getEmployee());
            dbUser.setWorkingPlaces(user.getWorkingPlaces());
            dbUser.setRoles(user.getRoles());
            userService.persist(dbUser);
            return "redirect:/user";
        }
        System.out.println("before error");
        if ( result.hasErrors() ) {
            System.out.println("result to string    "+result.toString());
            model.addAttribute("addStatus", false);
            model.addAttribute("employeeDetailShow", true);
            model.addAttribute("employeeNotFoundShow", false);
            model.addAttribute("roleList", roleService.findAll());
            model.addAttribute("province", Province.values());
            model.addAttribute("districtUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                    .build()
                    .toString());
            model.addAttribute("stationUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                    .build()
                    .toString());
            model.addAttribute("user", user);
            return "user/addUser";
        }
        System.out.println("affter error");
        //todo-> user is super senior office need to provide all working palace to check

        Employee employee = employeeService.findById(user.getEmployee().getId());
        if ( user.isEnabled() ) {
            user.setCreatedDate(dateTimeAgeService.getCurrentDate());
        }
        System.out.println(" imh ");
        // userService.persist(user);
        if ( employee != null ) {
            if ( employee.getEmployeeStatus().equals(EmployeeStatus.WORKING) ) {
                user.setEnabled(true);
            } else {
                user.setEnabled(false);
            }
            user.setCreatedDate(dateTimeAgeService.getCurrentDate());
            user.setEnabled(true);
            //User user1 = userService.persist(user);
        }
        System.out.println(" new plae");
        User user1 = userService.persist(user);
        System.out.println("User " + user1.toString());
        return "redirect:/user";
    }


    @RequestMapping( value = "/remove/{id}", method = RequestMethod.GET )
    public String removeUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/user";
    }

    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public String search(Model model, User user) {
        model.addAttribute("userDetail", userService.search(user));
        return "user/user-detail";
    }
}
