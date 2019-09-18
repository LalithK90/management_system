package lk.imms.management_system.asset.crime.service;

import lk.imms.management_system.asset.crime.dao.CourtDao;
import lk.imms.management_system.asset.crime.entity.Court;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtService implements AbstractService< Court, Long > {
    private final CourtDao courtDao;

    @Autowired
    public CourtService(CourtDao courtDao) {
        this.courtDao = courtDao;
    }

    @Override
    public List< Court > findAll() {
        return courtDao.findAll();
    }

    @Override
    public Court findById(Long id) {
        return courtDao.getOne(id);
    }

    @Override
    public Court persist(Court court) {
        //todo -> please find what area the main criteria to save cour to system
        // if some time need to verify court function
        return courtDao.save(court);
    }

    @Override
    public boolean delete(Long id) {
        //todo -> consider what are the fact need to check before delete
        courtDao.deleteById(id);
        return true;
    }

    @Override
    public List< Court > search(Court court) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Court > courtExample = Example.of(court, matcher);
        return courtDao.findAll(courtExample);
    }
}
