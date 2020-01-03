package lk.imms.management_system.asset.contravene.service;

import lk.imms.management_system.asset.contravene.dao.ContraveneDao;
import lk.imms.management_system.asset.contravene.entity.Contravene;
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
@CacheConfig( cacheNames = {"contravene"} ) // tells Spring where to store cache for this class
public class ContraveneService implements AbstractService< Contravene, Long > {
    private final ContraveneDao contraveneDao;

    @Autowired
    public ContraveneService(ContraveneDao contraveneDao) {
        this.contraveneDao = contraveneDao;
    }

    @Override
    @Cacheable("contravene")
    public List< Contravene > findAll() {
        return contraveneDao.findAll();
    }

    @Override
    public Contravene findById(Long id) {
        return contraveneDao.getOne(id);
    }

    @Override
    @CachePut("contravene")
    public Contravene persist(Contravene contravene) {
        return contraveneDao.save(contravene);
    }

    @Override
    //@CacheEvict(allEntries = true) if you want to flush all the cache
    @CacheEvict(value = "contravene",key = "#contravene.id" )
    public boolean delete(Long id) {
        //there are no possibilities to delete
        contraveneDao.deleteById(id);
        return true;
    }

    @Override
    public List< Contravene > search(Contravene contravene) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Contravene > contravenes = Example.of(contravene, matcher);
        return contraveneDao.findAll(contravenes);
    }

    public List< Offender > findByOffendersUsingContravene(List< Contravene > contravenes) {
        List< Offender > offenders = new ArrayList<>();
        for ( Contravene contravene : contravenes ){
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Example< Contravene > contraveneExample = Example.of(contravene, matcher);
            for ( Contravene contravene1 : contraveneDao.findAll(contraveneExample) ){
                if ( contravene1.getOffenders() !=null ){
                    offenders.addAll(contravene1.getOffenders());
                }
            }
        }
        return offenders.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
