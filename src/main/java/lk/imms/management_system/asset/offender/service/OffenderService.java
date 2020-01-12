package lk.imms.management_system.asset.offender.service;

import lk.imms.management_system.asset.OffednerGuardian.service.GuardianService;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.offender.dao.OffenderDao;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.asset.petitionAddOffender.service.PetitionOffenderService;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"offender"} ) // tells Spring where to store cache for this class
public class OffenderService implements AbstractService< Offender, Long > {
    private final OffenderDao offenderDao;
    private final ContraveneService contraveneService;
    private final OffenderCallingNameService offenderCallingNameService;
    private final OffenderFilesService offenderFilesService;
    private final GuardianService guardianService;
    private final PetitionOffenderService petitionOffenderService;

    @Autowired
    public OffenderService(OffenderDao offenderDao, ContraveneService contraveneService,
                           OffenderCallingNameService offenderCallingNameService,
                           OffenderFilesService offenderFilesService, GuardianService guardianService,
                           PetitionOffenderService petitionOffenderService) {
        this.offenderDao = offenderDao;
        this.contraveneService = contraveneService;
        this.offenderCallingNameService = offenderCallingNameService;
        this.offenderFilesService = offenderFilesService;
        this.guardianService = guardianService;
        this.petitionOffenderService = petitionOffenderService;
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
    public List< Offender > search(Offender offender) {
        Offender searchOffender = new Offender();
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
            searchOffender.setId(offender.getId());
            offenders.addAll(searchAnyAttributeOffender(searchOffender));
            searchOffender.setId(null);
        }
        //registration number
        if ( offender.getOffenderRegisterNumber() != null ) {
            searchOffender.setOffenderRegisterNumber(offender.getOffenderRegisterNumber());
            offenders.addAll(searchAnyAttributeOffender(searchOffender));
            searchOffender.setOffenderRegisterNumber(null);
        }
        //name sinhala
        if ( offender.getNameSinhala() != null ) {
            commonSearchName(searchOffender, offender, offenders);
        }
        //name tamil
        if ( offender.getNameTamil() != null ) {
            commonSearchName(searchOffender, offender, offenders);
        }
        //name english
        if ( offender.getNameEnglish() != null ) {
            commonSearchName(searchOffender, offender, offenders);
        }
        //nic
        if ( offender.getNic() != null ) {
            searchOffender.setNic(offender.getNic());
            offenders.addAll(searchAnyAttributeOffender(searchOffender));
            searchOffender.setNic(null);
        }
        //passport
        if ( offender.getPassportNumber() != null ) {
            searchOffender.setPassportNumber(offender.getPassportNumber());
            offenders.addAll(searchAnyAttributeOffender(searchOffender));
            searchOffender.setPassportNumber(null);
        }
        //driving licence
        if ( offender.getDrivingLicenceNumber() != null ) {
            searchOffender.setDrivingLicenceNumber(offender.getDrivingLicenceNumber());
            offenders.addAll(searchAnyAttributeOffender(searchOffender));
            searchOffender.setDrivingLicenceNumber(null);
        }
        //mobile one
        if ( offender.getMobileOne() != null ) {
            commonSearchMobile(searchOffender, offenders, offender.getMobileOne());
        }
        //mobile two
        if ( offender.getMobileTwo() != null ) {
            commonSearchMobile(searchOffender, offenders, offender.getMobileTwo());
        }
        //land
        if ( offender.getLand() != null ) {
            commonSearchMobile(searchOffender, offenders, offender.getLand());
        }
        //email
        if ( offender.getEmail() != null ) {
            searchOffender.setEmail(offender.getEmail());
            offenders.addAll(searchAnyAttributeOffender(searchOffender));
            searchOffender.setEmail(null);
        }
        //description
        if ( offender.getDescription() != null ) {
            searchOffender.setDescription(offender.getDescription());
            offenders.addAll(searchAnyAttributeOffender(searchOffender));
            searchOffender.setDescription(null);
        }
        //guardian list
        if ( offender.getGuardians() != null ) {
            offenders.addAll(guardianService.findByOffendersUsingGuardian(offender.getGuardians()));
        }
        return searchAnyAttributeOffender(searchOffender);
    }

    //this used to find offender using any name in every were in db
    private void commonSearchName(Offender searchOffender, Offender offender, List< Offender > offenders) {
        //sinhala
        searchOffender.setNameSinhala(offender.getNameSinhala());
        offenders.addAll(searchAnyAttributeOffender(searchOffender));
        searchOffender.setNameSinhala(null);
        //tamil
        searchOffender.setNameSinhala(offender.getNameTamil());
        offenders.addAll(searchAnyAttributeOffender(searchOffender));
        searchOffender.setNameSinhala(null);
        //english
        searchOffender.setNameSinhala(offender.getNameEnglish());
        offenders.addAll(searchAnyAttributeOffender(searchOffender));
        searchOffender.setNameSinhala(null);
    }

    //this use any contact number using find
    private void commonSearchMobile(Offender searchOffender, List< Offender > offenders, String mobileNumber) {
        searchOffender.setMobileOne(mobileNumber);
        offenders.addAll(searchAnyAttributeOffender(searchOffender));
        searchOffender.setMobileOne(null);
        searchOffender.setMobileTwo(mobileNumber);
        offenders.addAll(searchAnyAttributeOffender(searchOffender));
        searchOffender.setMobileTwo(null);
        searchOffender.setLand(mobileNumber);
        offenders.addAll(searchAnyAttributeOffender(searchOffender));
        searchOffender.setLand(null);

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
