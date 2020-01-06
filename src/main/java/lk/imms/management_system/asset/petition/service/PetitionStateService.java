package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionStatusDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@Service
@CacheConfig( cacheNames = {"petitionState"} ) // tells Spring where to store cache for this class
public class PetitionStateService {
    private final PetitionStatusDao petitionStatusDao;

    @Autowired
    public PetitionStateService(PetitionStatusDao petitionStatusDao) {
        this.petitionStatusDao = petitionStatusDao;
    }

    @Caching( evict = {@CacheEvict( value = "petitionState", allEntries = true )},
            put = {@CachePut( value = "petitionState", key = "#petitionState.id" )} )
    public PetitionState persist(PetitionState petitionState) {
        return petitionStatusDao.save(petitionState);
    }

@Cacheable
    public PetitionState findByPetition(Petition petition) {
        return petitionStatusDao.findByPetition(petition);
    }
}
