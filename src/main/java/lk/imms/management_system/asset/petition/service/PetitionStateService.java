package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionStatusDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig( cacheNames = {"petitonState"} ) // tells Spring where to store cache for this class
public class PetitionStateService {
    private final PetitionStatusDao petitionStatusDao;

    @Autowired
    public PetitionStateService(PetitionStatusDao petitionStatusDao) {
        this.petitionStatusDao = petitionStatusDao;
    }

    @CachePut
    public PetitionState persist(PetitionState petitionState) {
        return petitionStatusDao.save(petitionState);
    }

    @Cacheable
    public PetitionState findByPetition(Petition petition) {
        return petitionStatusDao.findByPetition(petition);
    }
}
