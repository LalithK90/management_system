package lk.imms.management_system.asset.crime.service;

import lk.imms.management_system.asset.crime.dao.CrimeDao;
import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrimeService implements AbstractService< Crime, Long > {
    private final CrimeDao crimeDao;

    @Autowired
    public CrimeService(CrimeDao crimeDao) {
        this.crimeDao = crimeDao;
    }

    @Override
    public List< Crime > findAll() {
        return crimeDao.findAll();
    }

    @Override
    public Crime findById(Long id) {
        return crimeDao.getOne(id);
    }

    @Override
    public Crime persist(Crime crime)
    {
        //todo -> find fact before save
        return crimeDao.save(crime);
    }

    @Override
    public boolean delete(Long id) {
        //todo -> find fact what are the criteria before the delete
        crimeDao.deleteById(id);
        return true;
    }

    @Override
    public List< Crime > search(Crime crime) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Crime > crimeExample = Example.of(crime, matcher);
        return crimeDao.findAll(crimeExample);
    }
}
