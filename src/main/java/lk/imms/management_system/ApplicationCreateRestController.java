package lk.imms.management_system;

import lk.imms.management_system.asset.commonAsset.model.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.model.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.model.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.model.Enum.Title;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionerType;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import lk.imms.management_system.asset.userManagement.entity.Role;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.userManagement.service.RoleService;
import lk.imms.management_system.asset.userManagement.service.UserService;
import lk.imms.management_system.asset.workingPlace.entity.Enum.District;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.Enum.WorkingPlaceType;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.stream.Collectors;

@RestController
public class ApplicationCreateRestController {
    private final RoleService roleService;
    private final ContraveneService contraveneService;
    private final PetitionerService petitionerService;
    private final UserService userService;
    private final WorkingPlaceService workingPlaceService;
    private final EmployeeService employeeService;


    @Autowired
    public ApplicationCreateRestController(RoleService roleService, ContraveneService contraveneService,
                                           PetitionerService petitionerService, UserService userService,
                                           WorkingPlaceService workingPlaceService, EmployeeService employeeService) {
        this.roleService = roleService;
        this.contraveneService = contraveneService;
        this.petitionerService = petitionerService;
        this.userService = userService;
        this.workingPlaceService = workingPlaceService;
        this.employeeService = employeeService;
    }

    @GetMapping( "/select/user" )
    public String saveUser() {
        //roles list start
        String[] roles = {"ADMIN", "CGE", "ACGE", "CE", "DCL", "DCLE", "ACE", "SE", "OIC", "IE", "ESM", "ES", "EC",
                "EG", "ED"};
        for ( String s : roles ) {
            Role role = new Role();
            role.setRoleName(s);
            roleService.persist(role);
        }
//Working place
        WorkingPlace workingPlace = new WorkingPlace();
        workingPlace.setCode("ADMIN");
        workingPlace.setName("ADMIN");
        workingPlace.setProvince(Province.WP);
        workingPlace.setDistrict(District.CB);
        workingPlace.setWorkingPlaceType(WorkingPlaceType.HO);
        workingPlace.setAddress("Colombo , Head Office");
        workingPlace.setEmailOne("admin.hq@gmail.com");
        WorkingPlace workingPlaceDb = workingPlaceService.persist(workingPlace);

//Employee
        Employee employee = new Employee();
        employee.setPayRoleNumber("11111111");
        employee.setName("Admin User");
        employee.setCallingName("Admin");
        employee.setName("908670000V");
        employee.setMobileOne("0750000000");
        employee.setTitle(Title.DR);
        employee.setGender(Gender.MALE);
        employee.setBloodGroup(BloodGroup.AP);
        employee.setDesignation(Designation.ED);
        employee.setCivilStatus(CivilStatus.UNMARRIED);
        employee.setEmployeeStatus(EmployeeStatus.WORKING);
        employee.setDateOfBirth(LocalDate.now().minusYears(18));
        employee.setDateOfAssignment(LocalDate.now());
        employee.setWorkingPlace(workingPlaceDb);
        Employee employeeDb = employeeService.persist(employee);

        //contravene list save - start
        String[] code = {"PUM", "PUD", "POU", "UST", "UPT", "UPA", "USA", "USF", "COC", "CAN", "HRE", "OND", "CBC"
                , "OTR", "SCP", "SPP"};
        String[] details = {
                "Possession of Unlawfully Manufactured Liquor", "Possession of Unlawfully Distilled Spirit",
                "Possession of Utensil", "Unlawful Sale of Toddy", "Unlawful Possession of Toddy", "Unlawful " +
                "Possession of Arrack", "Unlawful Sale of Arrack"
                , "Unlawful Sale of Foreign Liquor", "Cocaine", "Cannabis", "Heroine", "Other Narcotic Drugs",
                "Contraband Cigarettes", "Other Tobacco Related", "Selling Cigarettes to persons under-age of" +
                " 21 yrs", "Smoking in Public Places"
        };
        for ( int i = 0; i < code.length; ) {
            Contravene contravene = new Contravene();
            contravene.setCode(code[i]);
            contravene.setDetail(details[i]);
            contravene.setId((long) ++i);
            contraveneService.persist(contravene);
        }
        // petitioner
        Petitioner presidentialSecretarial = new Petitioner();
        presidentialSecretarial.setAddress("Presidential Secretariat\n" +
                                      "Galle Face, Colombo 1\n" +
                                      "Sri Lanka");
        presidentialSecretarial.setEmail("ps@presidentsoffice.lk");
        presidentialSecretarial.setLand("0112345345");
        presidentialSecretarial.setNameEnglish("Presidential Secretariat");
        presidentialSecretarial.setNameSinhala("ජනාධිපති ලේකම් කාර්යාලය");
        presidentialSecretarial.setNameTamil("சனாதிபதி செயலகம்");
        presidentialSecretarial.setPetitionerType(PetitionerType.PRESIDENT);
        petitionerService.persist(presidentialSecretarial);
// petitioner
        Petitioner primeMinisterOffice = new Petitioner();
        primeMinisterOffice.setAddress("No: 58, Sir Ernest De Silva Mawatha,\n" +
                                      "Colombo 07.\n" +
                                      "Sri Lanka.");
        primeMinisterOffice.setEmail("info@pmoffice.gov.lk");
        primeMinisterOffice.setLand("0112575317");
        primeMinisterOffice.setNameEnglish("Prime Minister's Office");
        primeMinisterOffice.setNameSinhala("අග්‍රාමාත්‍ය කාර්යාලය");
        primeMinisterOffice.setNameTamil("சோசலிச குடியரசின்");
        primeMinisterOffice.setPetitionerType(PetitionerType.RRIMEMINISTER);
        petitionerService.persist(primeMinisterOffice);
// petitioner
        Petitioner NDDCB = new Petitioner();
        NDDCB.setAddress("No 383, Kotte Road,\\r\\nRajagiriya.");
        NDDCB.setEmail("dg@nddcb.gov.lk");
        NDDCB.setLand("0112873718");
        NDDCB.setNameEnglish("National Dangerous Drugs Control Board");
        NDDCB.setNameSinhala("අන්තරායකර ඖෂධ පාලක ජාතික මණ්ඩලය");
        NDDCB.setNameTamil("National Dangerous Drugs Control Board");
        NDDCB.setPetitionerType(PetitionerType.NDDCA);
        petitionerService.persist(NDDCB);

        //admin user one
        User user = new User();
        user.setEmployee(employeeDb);
        user.setUsername("admin");
        user.setPassword("admin");
        String message = "Username:- "+ user.getUsername()+"\nPassword:- "+ user.getPassword();
        user.setEnabled(true);
        user.setRoles(roleService.findAll()
                              .stream()
                              .filter(role -> role.getRoleName().equals("ADMIN"))
                              .collect(Collectors.toList()));
        user.setWorkingPlaces(workingPlaceService.findAll());
        userService.persist(user);

       return message;
    }


}
