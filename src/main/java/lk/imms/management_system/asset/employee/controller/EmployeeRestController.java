package lk.imms.management_system.asset.employee.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.Enum.Designation;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/employee" )
public class EmployeeRestController {
    private final EmployeeService employeeService;
    private final WorkingPlaceService workingPlaceService;

    public EmployeeRestController(EmployeeService employeeService, WorkingPlaceService workingPlaceService) {
        this.employeeService = employeeService;
        this.workingPlaceService = workingPlaceService;
    }

    @GetMapping( value = "/getEmployee" )
    public MappingJacksonValue getEmployeeByWorkingPlace(@RequestParam( "designation" ) String designation,
                                                         @RequestParam( "id" ) Long id) {
        Employee employee = new Employee();
        employee.setWorkingPlace(workingPlaceService.findById(id));
        employee.setDesignation(Designation.valueOf(designation));

        //MappingJacksonValue
        List< Employee > employees = employeeService.search(employee);
        //employeeService.findByWorkingPlace(workingPlaceService.findById(id));

        //Create new mapping jackson value and set it to which was need to filter
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(employees);

        //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
        // what parameter's necessary to provide
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "payRoleNumber", "designation");
        //filters :-  set front end required value to before filter

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("Employee", simpleBeanPropertyFilter);
        //Employee :- need to annotate relevant class with JosonFilter  {@JsonFilter("Employee") }
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

}
