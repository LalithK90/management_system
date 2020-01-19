package lk.imms.management_system.asset.petitioner.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.petition.entity.Enum.PetitionerType;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import lk.imms.management_system.asset.petitioner.service.PetitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/petitionerRest" )
public class PetitionerRestController {
    private final PetitionerService petitionerService;

    @Autowired
    public PetitionerRestController(PetitionerService petitionerService) {
        this.petitionerService = petitionerService;
    }

    @GetMapping( "getPetitioner/{petitionerType}" )
    public MappingJacksonValue getPetitioner(@PathVariable( "petitionerType" ) PetitionerType petitionerType) {

        //MappingJacksonValue
        Petitioner  petitioner = petitionerService.findByPetitionerType(petitionerType);
        //employeeService.findByWorkingPlace(workingPlaceService.findById(id));

        //Create new mapping jackson value and set it to which was need to filter
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(petitioner);

        //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
        // what parameter's necessary to provide
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "nameSinhala", "nameTamil","nameEnglish","address","mobileOne" ,"mobileTwo","land","email","createdBy");
        //filters :-  set front end required value to before filter

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("Petitioner", simpleBeanPropertyFilter);
        //Employee :- need to annotate relevant class with JosonFilter  {@JsonFilter("Employee") }
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
