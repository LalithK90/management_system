package lk.imms.management_system.asset.petitioner.service;

import lk.imms.management_system.asset.petition.entity.Enum.PetitionerType;
import lk.imms.management_system.asset.petitioner.dao.PetitionerDao;
import lk.imms.management_system.asset.petitioner.entity.Petitioner;
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
@CacheConfig( cacheNames = {"petitioner"} ) // tells Spring where to store cache for this class
public class PetitionerService implements AbstractService< Petitioner, Long > {
    private final PetitionerDao petitionerDao;

    @Autowired
    public PetitionerService(PetitionerDao petitionerDao) {
        this.petitionerDao = petitionerDao;
    }

    @Override
    @Cacheable
    public List< Petitioner > findAll() {
        return petitionerDao.findAll();
    }

    @Override
    @Cacheable
    public Petitioner findById(Long id) {
        return petitionerDao.getOne(id);
    }

    @Override
    @CachePut
    public Petitioner persist(Petitioner petitioner) {
        return petitionerDao.save(petitioner);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        petitionerDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    public List< Petitioner > search(Petitioner petitioner) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Petitioner > petitionerExample = Example.of(petitioner, matcher);
        return petitionerDao.findAll(petitionerExample);
    }

    public Petitioner findByPetitionerType(PetitionerType petitionerType) {
        String petitionerTypes = petitionerType.toString();
        return petitionerDao.findByPetitionerType(petitionerTypes);
    }
}
