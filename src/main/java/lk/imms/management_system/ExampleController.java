package lk.imms.management_system;


import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/supper" )
public class ExampleController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String supperUserCreate() {


/*
        Employee employee = new Employee();
        employee.setNumber("PB0001");
        employee.setTitle(Title.MR);
        employee.setName("Sammera Banda");
        employee.setCallingName("sameera");
        employee.setGender(Gender.MALE);
        employee.setNic("900600900V");
        employee.setDateOfBirth(LocalDate.now());
        employee.setCivilStatus(CivilStatus.UNMARRIED);
        employee.setEmail("asaderc@gmail.com");
        employee.setMobile("0772350400");
        employee.setLand("");
        employee.setAddress("hahahhaha");
        employee.setDesignation(Designation.MANAGER);
        employee.setEmployeeStatus(EmployeeStatus.WORKING);
        employee.setDateOfAssignment(LocalDate.now());

        List<Role> roles = new ArrayList<>();

        Role role = new Role();
        role.setRoleName("MANAGER");
        roles.add(role);
        Role role1 = new Role();
        role1.setRoleName("CASHIER");
        roles.add(role1);
        Role role2 = new Role();
        role2.setRoleName("GRV");
        roles.add(role2);


        User user = new User();
        user.setEmployee(employee);
        user.setUsername("sameera");
        user.setCreatedDate(LocalDate.now());
        user.setEnabled(true);
        user.setRoles(roles);
        user.setPassword("12345");



        return userService.persist(user);
*/
        String username = "";
        String password = "";


        String message = "<h1><strong><i>Successful Supper User Created</i></strong> </h1>" +
                "<div class=\"col-sm-8\"> " +
                "<span class=\"text-warning\">Your Login Credential is </br>" +
                "User name is :" + username + "</br>" +
                "Password is :" + password +
                "</span>" +
                "</div>" +
                "</br> <p> click to following button to Login to the system.</p>" +
                "<div class = \"col-sm-8 text-center\">" +
                "<a href=\"supperUserLogin\" target=\"_self\">" +
                "<input type=\"button\" class=\"btn btn-info btn-lg\"" +
                "</a>" +
                "</div>";

        return message;
    }
}

