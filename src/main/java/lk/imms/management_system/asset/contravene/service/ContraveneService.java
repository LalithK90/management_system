package lk.imms.management_system.asset.contravene.service;

import lk.imms.management_system.asset.contravene.dao.ContraveneDao;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"contravene"} ) // tells Spring where to store cache for this class
public class ContraveneService implements AbstractService< Contravene, Long > {
    private final ContraveneDao contraveneDao;

    @Autowired
    public ContraveneService(ContraveneDao contraveneDao) {
        this.contraveneDao = contraveneDao;
    }

    @Override
    @Cacheable
    public List< Contravene > findAll() {
        return contraveneDao.findAll();
    }

    @Override
    @Cacheable
    public Contravene findById(Long id) {
        return contraveneDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "contravene", allEntries = true )},
            put = {@CachePut( value = "contravene", key = "#contravene.id" )} )
    public Contravene persist(Contravene contravene) {
        return contraveneDao.save(contravene);
    }

    @Override
    @CacheEvict(allEntries = true) //if you want to flush all the cache
    public boolean delete(Long id) {
        //there are no possibilities to delete
        contraveneDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    public List< Contravene > search(Contravene contravene) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Contravene > contravenes = Example.of(contravene, matcher);
        return contraveneDao.findAll(contravenes);
    }
}
