package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionStatusDao;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetitionStateService {
    private final PetitionStatusDao petitionStatusDao;

    @Autowired
    public PetitionStateService(PetitionStatusDao petitionStatusDao) {
        this.petitionStatusDao = petitionStatusDao;
    }

    public PetitionState persist(PetitionState petitionState){
        return petitionStatusDao.save(petitionState);
    }
}
