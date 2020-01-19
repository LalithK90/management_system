package lk.imms.management_system.asset.offender.service;

import lk.imms.management_system.asset.offender.dao.OffenderCallingNameDao;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.entity.OffenderCallingName;
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
@CacheConfig( cacheNames = {"offenderCallingName"} ) // tells Spring where to store cache for this class
public class OffenderCallingNameService implements AbstractService< OffenderCallingName, Long > {
    private final OffenderCallingNameDao offenderCallingNameDao;

    @Autowired
    public OffenderCallingNameService(OffenderCallingNameDao offenderCallingNameDao) {
        this.offenderCallingNameDao = offenderCallingNameDao;
    }

    @Override
    @Cacheable
    public List< OffenderCallingName > findAll() {
        return offenderCallingNameDao.findAll();
    }

    @Override
    @Cacheable
    public OffenderCallingName findById(Long id) {
        return offenderCallingNameDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "offenderCallingName", allEntries = true )},
            put = {@CachePut( value = "offenderCallingName", key = "#offenderCallingName.id" )} )
    public OffenderCallingName persist(OffenderCallingName offenderCallingName) {
        return offenderCallingNameDao.save(offenderCallingName);
    }

    @Override
    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //there should not be possibilities to delete
        offenderCallingNameDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    public List< OffenderCallingName > search(OffenderCallingName offenderCallingName) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< OffenderCallingName > offenderCallingNameExample = Example.of(offenderCallingName, matcher);
        return offenderCallingNameDao.findAll(offenderCallingNameExample);
    }

    @Cacheable
    public List< Offender > findByOffendersUsingCallingNames(List< OffenderCallingName > offenderCallingNames) {
        List< Offender > offenders = new ArrayList<>();
        offenderCallingNames.forEach(callingName -> {
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Example< OffenderCallingName > callingNameExample = Example.of(callingName, matcher);
            offenderCallingNameDao.findAll(callingNameExample).forEach(callingName1 -> offenders.add(callingName1.getOffender()));

        });
        return offenders.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
