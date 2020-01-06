package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@CacheConfig( cacheNames = {"petition"} ) // tells Spring where to store cache for this class
public class PetitionService implements AbstractService< Petition, Long > {
    private final PetitionDao petitionDao;

    @Autowired
    public PetitionService(PetitionDao petitionDao) {
        this.petitionDao = petitionDao;
    }

    @Override
    //@Cacheable("petition")
    public List< Petition > findAll() {
        return petitionDao.findAll();
    }

    @Override
    //@Cacheable("petition")
    public Petition findById(Long id) {
        return petitionDao.getOne(id);
    }

    @Override
    //@CachePut("petition")
    public Petition persist(Petition petition) {
        return petitionDao.save(petition);
    }

    @Override
    //@CacheEvict("petition")
    public boolean delete(Long id) {
        petitionDao.deleteById(id);
        return true;
    }

    @Override
    public List< Petition > search(Petition petition) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Petition > petitionExample = Example.of(petition, matcher);
        return petitionDao.findAll(petitionExample);
    }

    public Petition getLastOne() {
        return petitionDao.findFirstByOrderByIdDesc();
    }
}
