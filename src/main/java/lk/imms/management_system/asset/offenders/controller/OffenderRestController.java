package lk.imms.management_system.asset.offenders.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.asset.offenders.service.OffenderService;
import lk.imms.management_system.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OffenderRestController {
    private final OffenderService offenderService;
    private final DateTimeAgeService dateTimeAgeService;

    @Autowired
    public OffenderRestController(OffenderService offenderService, DateTimeAgeService dateTimeAgeService) {
        this.offenderService = offenderService;
        this.dateTimeAgeService = dateTimeAgeService;
    }

    @GetMapping( value = "/getEmployee" )
    public MappingJacksonValue getEmployeeByWorkingPlace(@RequestParam( "designation" ) String designation,
                                                         @RequestParam( "id" ) Long id) {


        //MappingJacksonValue
        List< Offender > offenders = offenderService.findAll().stream()
                .distinct()
                .collect(Collectors.toList());
        //set all offenders their age
        for ( Offender offender : offenders ){
            if ( offender.getDateOfBirth() != null ){
                offender.setAge(dateTimeAgeService.getAgeString(offender.getDateOfBirth()));
            }
        }

        //Create new mapping jackson value and set it to which was need to filter
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(offenders);

        //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
        // what parameter's necessary to provide
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","offenderRegisterNumber", "nameEnglish", "nic","passportNumber","age");
        //filters :-  set front end required value to before filter

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("Offender", simpleBeanPropertyFilter);
        //Employee :- need to annotate relevant class with JosonFilter  {@JsonFilter("Employee") }
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
