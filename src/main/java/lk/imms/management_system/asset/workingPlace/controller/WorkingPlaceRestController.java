package lk.imms.management_system.asset.workingPlace.controller;

import lk.imms.management_system.asset.commonAsset.entity.DistrictList;
import lk.imms.management_system.asset.commonAsset.entity.StationList;
import lk.imms.management_system.asset.workingPlace.entity.Enum.District;
import lk.imms.management_system.asset.workingPlace.entity.Enum.Province;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.asset.workingPlace.service.WorkingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

@RestController
@RequestMapping( "/workingPlaceRest" )
public class WorkingPlaceRestController {
    private final WorkingPlaceService workingPlaceService;

    @Autowired
    public WorkingPlaceRestController(WorkingPlaceService workingPlaceService) {
        this.workingPlaceService = workingPlaceService;
    }

    /*return district relevant to the province*/
    @GetMapping( "/province/{province}" )
    public List< DistrictList > getDistrict(@PathVariable( "province" ) Province province) {
        List< DistrictList > districtLists = new ArrayList<>();
//Taken all working place from db
        HashMap< String, String > districts = new HashMap<>();
        for ( WorkingPlace workingPlace : workingPlaceService.findAll() ) {
            if ( province.equals(workingPlace.getProvince()) ) {
                districts.put(workingPlace.getDistrict().toString(), workingPlace.getDistrict().getDistrict());
            }
        }
        ArrayList< Entry< String, String > > entryArrayList =
                new ArrayList<>(districts.entrySet());
        for ( Entry< String, String > entry : entryArrayList ) {
            DistrictList districtList = new DistrictList();
            districtList.setName(entry.getKey());
            districtList.setDistrict(entry.getValue());
            districtLists.add(districtList);
        }
        return districtLists;
    }

    /*return station relevant to the district */
    @GetMapping( "/district/{district}" )
    public List< StationList > getStation(@PathVariable( "district" ) District district) {

        List< StationList > stationLists = new ArrayList<>();
        HashMap< Long, String > stations = new HashMap<>();
//Take all workstation and match with district and create new hash map
        for ( WorkingPlace workingPlace : workingPlaceService.findAll() ) {
            if ( district.equals(workingPlace.getDistrict()) ) {
                stations.put(workingPlace.getId(), workingPlace.getName());
            }
        }
        ArrayList< Entry< Long, String > > entryArrayList =
                new ArrayList<>(stations.entrySet());
        for ( Entry< Long, String > entry : entryArrayList ) {
            StationList stationList = new StationList();
            stationList.setId(entry.getKey());
            stationList.setName(entry.getValue());
            stationLists.add(stationList);
        }
        return stationLists;
    }
}

/*
  @GetMapping("/requestFilterValueFromEntity")
        public MappingJacksonValue filterParameter(){
            setEmployee();

    //Create new mapping jackson value and set it to which was need to filter

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(employee);

            //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was
            mentioned what parameter's necessary to provide
      SimpleBeanPropertyFilter simpleBeanPropertyFilter =  SimpleBeanPropertyFilter
                                                .filterOutAllExcept("id","name","email");
            //filters :-  set front end required value to before filter

            FilterProvider filters = new SimpleFilterProvider()
                                .addFilter("Employee", simpleBeanPropertyFilter);
            //Employee :- need to annotate relevant class with JosonFilter  {@JsonFilter("Employee") }
            mappingJacksonValue.setFilters(filters);

            return mappingJacksonValue;
        }

* */