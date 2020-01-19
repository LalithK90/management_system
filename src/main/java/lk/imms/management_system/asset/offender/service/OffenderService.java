package lk.imms.management_system.asset.offender.service;

import lk.imms.management_system.asset.OffednerGuardian.service.GuardianService;
import lk.imms.management_system.asset.commonAsset.service.CommonService;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.offender.dao.OffenderDao;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"offender"} ) // tells Spring where to store cache for this class
public class OffenderService implements AbstractService< Offender, Long > {
    private final OffenderDao offenderDao;
    private final ContraveneService contraveneService;
    private final OffenderCallingNameService offenderCallingNameService;
    private final OffenderFilesService offenderFilesService;
    private final GuardianService guardianService;
    private final CommonService commonService;

    @Autowired
    public OffenderService(OffenderDao offenderDao, ContraveneService contraveneService,
                           OffenderCallingNameService offenderCallingNameService,
                           OffenderFilesService offenderFilesService, GuardianService guardianService,
                           CommonService commonService) {
        this.offenderDao = offenderDao;
        this.contraveneService = contraveneService;
        this.offenderCallingNameService = offenderCallingNameService;
        this.offenderFilesService = offenderFilesService;
        this.guardianService = guardianService;
        this.commonService = commonService;
    }

    @Override
    @Cacheable
    public List< Offender > findAll() {
        List< Offender > offenders = new ArrayList<>();
        for ( Offender offender : offenderDao.findAll() ) {
            offender.setFileInfos(offenderFilesService.offenderFileDownloadLinks(offender));
            offenders.add(offender);
        }
        return offenders;
    }

    @Override
    @Cacheable
    public Offender findById(Long id) {
        return offenderDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "offender", allEntries = true )},
            put = {@CachePut( value = "offender", key = "#offender.id" )} )
    @Transactional
    public Offender persist(Offender offender) {

        return offenderDao.save(offender);
    }

    @Override
    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //there are no possibilities to delete an offender from system
        //more than 100 years need to save the in the system
        offenderDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    @Transactional
    public List< Offender > search(Offender offender) {
        //all offenders which all provided search, collect to this list
        List< Offender > offenders = new ArrayList<>();

        //offender calling name set
        if ( offender.getOffenderCallingNames() != null ) {
            offenders.addAll(offenderCallingNameService.findByOffendersUsingCallingNames(offender.getOffenderCallingNames()));
        }
        //contravene is there
        if ( offender.getPetitionOffenders() != null ) {
            List< Offender > offenders1 = new ArrayList<>();
            for ( PetitionOffender petitionOffender : offender.getPetitionOffenders() ) {
                for ( Contravene contravene : petitionOffender.getContravenes() ) {
                    contraveneService.findById(contravene.getId()).getPetitionOffenders()
                            .forEach(petitionOffender1 -> offenders1.add(petitionOffender1.getOffender()));
                }
            }
            offenders = offenders1;
        }
        //id
        if ( offender.getId() != null ) {
            offenders.add(offenderDao.getOne(offender.getId()));
        }
        //registration number
        if ( offender.getOffenderRegisterNumber() != null ) {
            offenders.add(offenderDao.findByOffenderRegisterNumber(offender.getOffenderRegisterNumber()));
        }
        //name sinhala
        if ( offender.getNameSinhala() != null ) {
            //sinhala
            offenders.addAll(commonSearchName(offender));
        }
        //name tamil
        if ( offender.getNameTamil() != null ) {
            //tamil
            offenders.addAll(commonSearchName(offender));
        }
        //name english
        if ( offender.getNameEnglish() != null ) {
            //english
            offenders.addAll(commonSearchName(offender));
        }
        //nic
        if ( offender.getNic() != null ) {
            offenders.add(offenderDao.findByNic(offender.getNic()));
        }
        //passport
        if ( offender.getPassportNumber() != null ) {
            offenders.add(offenderDao.findByPassportNumber(offender.getPassportNumber()));
        }
        //driving licence
        if ( offender.getDrivingLicenceNumber() != null ) {
            offenders.add(offenderDao.findByDrivingLicenceNumber(offender.getDrivingLicenceNumber()));
        }
        //mobile one
        if ( offender.getMobileOne() != null ) {
            offenders.addAll(commonSearchMobile(offender));
        }
        //mobile two
        if ( offender.getMobileTwo() != null ) {
            offenders.addAll(commonSearchMobile(offender));
        }
        //land
        if ( offender.getLand() != null ) {
            offenders.addAll(commonSearchMobile(offender));
        }
        //email
        if ( offender.getEmail() != null ) {
            offenders.add(offenderDao.findByEmail(offender.getEmail()));
        }
        //description
        if ( offender.getDescription() != null ) {
            offenders.addAll(offenderDao.findByDescription(offender.getDescription()));
        }
        //guardian list
        if ( offender.getGuardians() != null ) {
            offenders.addAll(guardianService.findByOffendersUsingGuardian(offender.getGuardians()));
        }
        return offenders;
    }

    //this used to find offender using any name in every were in db
    private List< Offender > commonSearchName(Offender offender) {
        List< Offender > offenders = new ArrayList<>();
        //sinhala
        if ( offender.getNameSinhala() != null ) {
            offenders.addAll(offenderDao.findByNameSinhala(offender.getNameSinhala()));
            offenders.addAll(offenderDao.findByNameTamil(offender.getNameSinhala()));
            offenders.addAll(offenderDao.findByNameEnglish(offender.getNameSinhala()));
            offenders
                    .stream()
                    .collect(Collectors
                                     .groupingBy(Function.identity(), Collectors.counting()))   // perform group by
                    // count
                    .entrySet().stream()
                    .filter(e -> e.getValue() > 1L)                                        // using take only those
                    // element whose count is greater than 1
                    .map(Map.Entry::getKey)                                                  // using map take only
                    // value
                    .collect(Collectors.toList());
        }
        //tamil
        if ( offender.getNameTamil() != null ) {
            offenders.addAll(offenderDao.findByNameSinhala(offender.getNameTamil()));
            offenders.addAll(offenderDao.findByNameTamil(offender.getNameTamil()));
            offenders.addAll(offenderDao.findByNameEnglish(offender.getNameTamil()));
            offenders
                    .stream()
                    .collect(Collectors
                                     .groupingBy(Function.identity(), Collectors.counting()))   // perform group by
                    // count
                    .entrySet().stream()
                    .filter(e -> e.getValue() > 1L)                                        // using take only those
                    // element whose count is greater than 1
                    .map(Map.Entry::getKey)                                                  // using map take only
                    // value
                    .collect(Collectors.toList());
        }
        //english
        if ( offender.getNameEnglish() != null ) {
            offenders.addAll(offenderDao.findByNameSinhala(offender.getNameEnglish()));
            offenders.addAll(offenderDao.findByNameTamil(offender.getNameEnglish()));
            offenders.addAll(offenderDao.findByNameEnglish(offender.getNameEnglish()));
            offenders
                    .stream()
                    .collect(Collectors
                                     .groupingBy(Function.identity(), Collectors.counting()))   // perform group by
                    // count
                    .entrySet().stream()
                    .filter(e -> e.getValue() > 1L)                                        // using take only those
                    // element whose count is greater than 1
                    .map(Map.Entry::getKey)                                                  // using map take only
                    // value
                    .collect(Collectors.toList());
        }
        return offenders;
    }

    //this use any contact number using find
    private List< Offender > commonSearchMobile(Offender offender) {
        List< Offender > offenders = new ArrayList<>();
        //mobile one
        if ( offender.getMobileOne() != null ) {
            final String mobileFinal = commonService.commonMobileNumberLengthValidator(offender.getMobileOne());
            offenders.addAll(offenderDao.findByMobileOne(mobileFinal));
            offenders.addAll(offenderDao.findByMobileTwo(mobileFinal));
            offenders.addAll(offenderDao.findByLand(mobileFinal));
            offenders.stream()
                    .filter(offender1 -> offender1.getMobileOne().equals(mobileFinal))
                    .collect(Collectors.toList());
        }
        //mobile two
        if ( offender.getMobileTwo() != null ) {
            final String mobileFinal = commonService.commonMobileNumberLengthValidator(offender.getMobileTwo());
            offenders.addAll(offenderDao.findByMobileOne(mobileFinal));
            offenders.addAll(offenderDao.findByMobileTwo(mobileFinal));
            offenders.addAll(offenderDao.findByLand(mobileFinal));
            offenders.stream()
                    .filter(offender1 -> offender1.getMobileOne().equals(mobileFinal))
                    .collect(Collectors.toList());
        }
        //mobile land
        if ( offender.getLand() != null ) {
            final String landFinal = commonService.commonMobileNumberLengthValidator(offender.getLand());
            offenders.addAll(offenderDao.findByMobileOne(landFinal));
            offenders.addAll(offenderDao.findByMobileTwo(landFinal));
            offenders.addAll(offenderDao.findByLand(landFinal));
            offenders.stream()
                    .filter(offender1 -> offender1.getLand().equals(landFinal))
                    .collect(Collectors.toList());
        }
        return offenders;
    }

    //using dao search employee
    private List< Offender > searchAnyAttributeOffender(Offender offender) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Offender > offenderExample = Example.of(offender, matcher);
        return offenderDao.findAll(offenderExample);
    }

    public Offender getLastOne() {
        return offenderDao.findFirstByOrderByIdDesc();
    }
}
