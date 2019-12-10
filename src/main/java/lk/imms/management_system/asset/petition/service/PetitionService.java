package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
        return petitionDao.getOne(id);
    }

    @Override
    public Petition persist(Petition petition) {
        return petitionDao.save(petition);
    }

    @Override
    public boolean delete(Long id)
    {
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
}
