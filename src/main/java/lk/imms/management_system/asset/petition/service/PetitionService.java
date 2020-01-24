package lk.imms.management_system.asset.petition.service;


import lk.imms.management_system.asset.petition.dao.PetitionDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig( cacheNames = {"petition"} ) // tells Spring where to store cache for this class
public class PetitionService implements AbstractService< Petition, Long > {
    private final PetitionDao petitionDao;

    @Autowired
    public PetitionService(PetitionDao petitionDao) {
        this.petitionDao = petitionDao;
    }

    @Override
    @Cacheable( "petition" )
    public List< Petition > findAll() {
        return petitionDao.findAllByOrderByIdDesc();
    }

    @Override
    @Cacheable( "petition" )
    public Petition findById(Long id) {
        return petitionDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "petition", allEntries = true )},
            put = {@CachePut( value = "petition", key = "#petition.id" )} )
    @Transactional
    public Petition persist(Petition petition) {
        return petitionDao.save(petition);
    }

    @Override
    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
       // petitionDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    public List< Petition > search(Petition petition) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Petition > petitionExample = Example.of(petition, matcher);
        return petitionDao.findAll(petitionExample);
    }

    @Transactional(readOnly = true)
    public Petition getLastOne() {
        return petitionDao.findFirstByOrderByIdDesc();
    }

    @Cacheable
    @Transactional
    public List< Petition > searchAnyParameter(Petition petition) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Petition > petitionExample = Example.of(petition, matcher);

        return petitionDao.findAll(petitionExample);
    }

    @Cacheable
    public Long countByWorkingPlaceAndCreatedAtBetween(WorkingPlace workingPlace, LocalDateTime from,
                                                       LocalDateTime to) {
        return petitionDao.countByWorkingPlaceAndCreatedAtBetween(workingPlace, from, to);
    }
}
