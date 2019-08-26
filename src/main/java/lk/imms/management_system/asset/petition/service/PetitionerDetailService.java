package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionerDetailDao;
import lk.imms.management_system.asset.petition.entity.PetitionerDetail;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetitionerDetailService implements AbstractService< PetitionerDetail, Long > {
    private final PetitionerDetailDao petitionerDetailDao;

    @Autowired
    public PetitionerDetailService(PetitionerDetailDao petitionerDetailDao) {
        this.petitionerDetailDao = petitionerDetailDao;
    }

    @Override
    public List< PetitionerDetail > findAll() {
        return petitionerDetailDao.findAll();
    }

    @Override
    public PetitionerDetail findById(Long id) {
        return null;
    }

    @Override
    public PetitionerDetail persist(PetitionerDetail petitionerDetail) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< PetitionerDetail > search(PetitionerDetail petitionerDetail) {
        return null;
    }
}
