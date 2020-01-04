package lk.imms.management_system.asset.OffednerGuardian.service;

import lk.imms.management_system.asset.OffednerGuardian.dao.GuardianDao;
import lk.imms.management_system.asset.OffednerGuardian.entity.Guardian;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"guardian"} )
public class GuardianService implements AbstractService< Guardian, Long > {
    private final GuardianDao guardianDao;

    @Autowired
    public GuardianService(GuardianDao guardianDao) {
        this.guardianDao = guardianDao;
    }

    @Override
    @Cacheable
    public List< Guardian > findAll() {
        return guardianDao.findAll();
    }

    @Override
    @Cacheable
    public Guardian findById(Long id) {
        return guardianDao.getOne(id);
    }

    @Override
    @CachePut
    public Guardian persist(Guardian guardian) {
        return guardianDao.save(guardian);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        guardianDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    public List< Guardian > search(Guardian guardian) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Guardian > guardianExample = Example.of(guardian, matcher);
        return guardianDao.findAll(guardianExample);
    }

    public List< Offender > findByOffendersUsingGuardian(List< Guardian > guardians) {
        List< Offender > offenders = new ArrayList<>();
        guardians.forEach(guardian -> {
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Example< Guardian > guardianExample = Example.of(guardian, matcher);
            guardianDao.findAll(guardianExample).forEach(guardian1 -> offenders.add(guardian1.getOffender()));

        });
        return offenders.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public Guardian findByNic(String nic) {
        return guardianDao.findByNic(nic);
    }
}
