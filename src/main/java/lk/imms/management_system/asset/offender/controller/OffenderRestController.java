package lk.imms.management_system.asset.offender.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.service.OffenderFilesService;
import lk.imms.management_system.asset.offender.service.OffenderService;
import lk.imms.management_system.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OffenderRestController {
    private final OffenderService offenderService;
    private final DateTimeAgeService dateTimeAgeService;
    private final OffenderFilesService offenderFilesService;

    @Autowired
    public OffenderRestController(OffenderService offenderService, DateTimeAgeService dateTimeAgeService,
                                  OffenderFilesService offenderFilesService) {
        this.offenderService = offenderService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.offenderFilesService = offenderFilesService;
    }

    @PostMapping("/getOffender")
    public MappingJacksonValue getOffender(@RequestBody Offender offender) {
        System.out.println("come offender " + offender.toString());
        //MappingJacksonValue
        List< Offender > offenders = offenderService.findAll().stream()
                .distinct()
                .collect(Collectors.toList());
        //set all offenders their age
        for ( Offender offender1 : offenders ){
            if ( offender1.getDateOfBirth() != null ){
                offender1.setAge(dateTimeAgeService.getAgeString(offender1.getDateOfBirth()));
            }
            offender1.setFileInfos(offenderFilesService.offenderFileDownloadLinks(offender1));
        }

        //Create new mapping jackson value and set it to which was need to filter
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(offenders);

        //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
        // what parameter's necessary to provide
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","offenderRegisterNumber", "nameEnglish", "nic","passportNumber","age","fileInfos");
        //filters :-  set front end required value to before filter

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("Offender", simpleBeanPropertyFilter);
        //Employee :- need to annotate relevant class with JosonFilter  {@JsonFilter("Employee") }
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }


}
