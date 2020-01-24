package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionStatuDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = {"petitionState"} ) // tells Spring where to store cache for this class
public class PetitionStateService {
    private final PetitionStatuDao petitionStatuDao;

    @Autowired
    public PetitionStateService(PetitionStatuDao petitionStatuDao) {
        this.petitionStatuDao = petitionStatuDao;
    }

    @Caching( evict = {@CacheEvict( value = "petitionState", allEntries = true )},
            put = {@CachePut( value = "petitionState", key = "#petitionState.id" )} )
    public PetitionState persist(PetitionState petitionState) {
        return petitionStatuDao.save(petitionState);
    }

    @Cacheable
    public List<PetitionState> findByPetition(Petition petition) {
        return petitionStatuDao.findByPetition(petition);
    }
}
