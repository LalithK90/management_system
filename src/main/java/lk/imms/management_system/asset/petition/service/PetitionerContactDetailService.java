package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionerContactDetailDao;
import lk.imms.management_system.asset.petition.entity.PetitionerContactDetail;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetitionerContactDetailService implements AbstractService< PetitionerContactDetail, Long > {
    private final PetitionerContactDetailDao petitionerContactDetailDao;

    @Autowired
    public PetitionerContactDetailService(PetitionerContactDetailDao petitionerContactDetailDao) {
        this.petitionerContactDetailDao = petitionerContactDetailDao;
    }

    @Override
    public List< PetitionerContactDetail > findAll() {
        return petitionerContactDetailDao.findAll();
    }

    @Override
    public PetitionerContactDetail findById(Long id) {
        return null;
    }

    @Override
    public PetitionerContactDetail persist(PetitionerContactDetail petitionerContactDetail) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< PetitionerContactDetail > search(PetitionerContactDetail petitionerContactDetail) {
        return null;
    }
}
