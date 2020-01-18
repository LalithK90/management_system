package lk.imms.management_system.asset.minutePetition.service;


import lk.imms.management_system.asset.minutePetition.dao.MinutePetitionDao;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig( cacheNames = {"minutePetition"} ) // tells Spring where to store cache for this class
public class MinutePetitionService implements AbstractService< MinutePetition, Long > {
    private final MinutePetitionDao minutePetitionDao;

    @Autowired
    public MinutePetitionService(MinutePetitionDao minutePetitionDao) {
        this.minutePetitionDao = minutePetitionDao;
    }

    @Override
    @Cacheable
    public List< MinutePetition > findAll() {
        return minutePetitionDao.findAll();
    }

    @Override
    @Cacheable
    public MinutePetition findById(Long id) {
        return minutePetitionDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "minutePetition", allEntries = true )},
            put = {@CachePut( value = "minutePetition", key = "#minutePetition.id" )} )
    @Transactional
    public MinutePetition persist(MinutePetition minutePetition) {
        return minutePetitionDao.save(minutePetition);
    }

    @Override
    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        // there is no possibility to delete any minutePetition
        return false;
    }

    @Override
    @Cacheable
    public List< MinutePetition > search(MinutePetition minutePetition) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< MinutePetition > minuteExample = Example.of(minutePetition, matcher);
        return minutePetitionDao.findAll(minuteExample);
    }

    @Cacheable
    public List< MinutePetition > findByPetition(Petition petition) {
        return minutePetitionDao.findByPetition(petition);
    }
}
