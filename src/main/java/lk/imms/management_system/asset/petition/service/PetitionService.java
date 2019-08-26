package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetitionService implements AbstractService< Petition, Long > {
    private final PetitionDao petitionDao;

    @Autowired
    public PetitionService(PetitionDao petitionDao) {
        this.petitionDao = petitionDao;
    }

    @Override
    public List< Petition > findAll() {
        return petitionDao.findAll();
    }

    @Override
    public Petition findById(Long id) {
        return null;
    }

    @Override
    public Petition persist(Petition petition) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< Petition > search(Petition petition) {
        return null;
    }
}
