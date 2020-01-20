package lk.imms.management_system;

import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import lk.imms.management_system.asset.userManagement.entity.Role;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserCreateRestController {
    private final RoleService roleService;


    List< WorkingPlace > workingPlaceList = new ArrayList<>();

    @Autowired
    public UserCreateRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    //save roles
    @GetMapping( "/role1" )
    public List<Role> saveRole() {
        String[] roles = {"ADMIN", "CGE", "ACGE", "CE", "DCL", "DCLE", "ACE", "SE", "OIC", "IE", "ESM", "ES", "EC",
                "EG", "ED"};
        List< Role > roleList = new ArrayList<>();
        for ( String s : roles ) {
            Role role = new Role();
            role.setRoleName(s);
            roleList.add(roleService.persist(role));
        }
        return roleList;
    }

    //working Place
    @GetMapping( "/workingPlace1" )
    public WorkingPlace saveWorkingPlace() {
        WorkingPlace workingPlace = new WorkingPlace();

        return workingPlace;
    }

    @GetMapping("/user1")
    public User saveUser(){
        User user = new User();

        return user;
    }
    @GetMapping("/petitionerStart1")
    public Petitioner savePetitioner(){
        Petitioner petitioner = new Petitioner();

        return petitioner;
    }

}
