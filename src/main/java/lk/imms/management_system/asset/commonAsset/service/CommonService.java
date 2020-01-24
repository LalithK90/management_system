package lk.imms.management_system.asset.commonAsset.service;

import lk.imms.management_system.asset.commonAsset.model.Enum.BloodGroup;
import lk.imms.management_system.asset.commonAsset.model.Enum.CivilStatus;
import lk.imms.management_system.asset.commonAsset.model.Enum.Gender;
import lk.imms.management_system.asset.commonAsset.model.Enum.Title;
import lk.imms.management_system.asset.employee.controller.EmployeeRestController;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.entity.Enum.EmployeeStatus;
import lk.imms.management_system.asset.workingPlace.controller.WorkingPlaceRestController;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class CommonService {
    //common things to employee and offender - start
    public void commonUrlBuilder(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("designations", Designation.values());
        model.addAttribute("provinces", Province.values());
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
    }

    public void commonEmployeeAndOffender(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
    }

    //common things to employee and offender - end
    //common mobile number length employee,offender,guardian, petitioner - start
    // private final mobile length validator
    public String commonMobileNumberLengthValidator(String number) {
        if ( number.length() == 9 ) {
            number = "0".concat(number);
        }
        return number;
    }

}
