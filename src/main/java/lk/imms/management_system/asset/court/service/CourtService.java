package lk.imms.management_system.asset.court.service;

import lk.imms.management_system.asset.court.dao.CourtDao;
import lk.imms.management_system.asset.court.entity.Court;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "court")
public class CourtService implements AbstractService< Court, Long > {
    private final CourtDao courtDao;

    @Autowired
    public CourtService(CourtDao courtDao) {
        this.courtDao = courtDao;
    }

    @Override
    @Cacheable
    public List< Court > findAll() {
        return courtDao.findAll();
    }

    @Override
    @Cacheable
    public Court findById(Long id) {
        return courtDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "court", allEntries = true )},
            put = {@CachePut( value = "court", key = "#court.id" )} )
    public Court persist(Court court) {
        return courtDao.save(court);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
       courtDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    public List< Court > search(Court court) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Court > courtExample = Example.of(court, matcher);
        return courtDao.findAll(courtExample);
    }
}
