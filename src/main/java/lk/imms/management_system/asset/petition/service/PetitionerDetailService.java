package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionerDetailDao;
import lk.imms.management_system.asset.petition.entity.PetitionerDetail;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
        return petitionerDetailDao.getOne(id);
    }

    @Override
    public PetitionerDetail persist(PetitionerDetail petitionerDetail) {
        return petitionerDetailDao.save(petitionerDetail);
    }

    @Override
    public boolean delete(Long id) {
        petitionerDetailDao.deleteById(id);
        return true;
    }

    @Override
    public List< PetitionerDetail > search(PetitionerDetail petitionerDetail) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< PetitionerDetail > petitionerDetailExample = Example.of(petitionerDetail, matcher);
        return petitionerDetailDao.findAll(petitionerDetailExample);
    }
}
