package lk.imms.management_system.asset.petitionAddOffender.service;

import lk.imms.management_system.asset.petitionAddOffender.dao.PetitionOffenderDao;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"petitionOffender"})
public class PetitionOffenderService implements AbstractService< PetitionOffender, Long > {
    private final PetitionOffenderDao petitionOffenderDao;

    @Autowired
    public PetitionOffenderService(PetitionOffenderDao petitionOffenderDao) {
        this.petitionOffenderDao = petitionOffenderDao;
    }

    @Override
    @Cacheable
    public List< PetitionOffender > findAll() {
        return petitionOffenderDao.findAll();
    }

    @Override
    @Cacheable
    public PetitionOffender findById(Long id) {
        return petitionOffenderDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "petitionOffender", allEntries = true )},
            put = {@CachePut( value = "petitionOffender", key = "#petitionOffender.id" )} )
    public PetitionOffender persist(PetitionOffender petitionOffender) {
        return petitionOffenderDao.save(petitionOffender);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        //this is not applicable
        return false;
    }

    @Override
    @Cacheable
    public List< PetitionOffender > search(PetitionOffender petitionOffender) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< PetitionOffender > petitionOffenderExample = Example.of(petitionOffender, matcher);
        return petitionOffenderDao.findAll(petitionOffenderExample);
    }
    @Caching( evict = {@CacheEvict( value = "petitionOffender", allEntries = true )},
            put = {@CachePut( value = "petitionOffender", key = "#petitionOffender.id" )} )
    public List<PetitionOffender> persistAll(List<PetitionOffender> petitionOffenders) {
        return petitionOffenderDao.saveAll(petitionOffenders);
    }

}
