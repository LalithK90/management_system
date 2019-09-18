package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.OffenderCallingNameDao;
import lk.imms.management_system.asset.offenders.entity.OffenderCallingName;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffenderCallingNameService implements AbstractService< OffenderCallingName, Long > {
    private final OffenderCallingNameDao offenderCallingNameDao;

    @Autowired
    public OffenderCallingNameService(OffenderCallingNameDao offenderCallingNameDao) {
        this.offenderCallingNameDao = offenderCallingNameDao;
    }


    @Override
    public List< OffenderCallingName > findAll() {
        return offenderCallingNameDao.findAll();
    }

    @Override
    public OffenderCallingName findById(Long id) {
        return offenderCallingNameDao.getOne(id);
    }

    @Override
    public OffenderCallingName persist(OffenderCallingName offenderCallingName) {
        //todo -> find fact before save
        return offenderCallingNameDao.save(offenderCallingName);
    }

    @Override
    public boolean delete(Long id) {
        //there should not be possibilities to delete
        offenderCallingNameDao.deleteById(id);
        return true;
    }

    @Override
    public List< OffenderCallingName > search(OffenderCallingName offenderCallingName) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< OffenderCallingName > offenderCallingNameExample = Example.of(offenderCallingName, matcher);
        return offenderCallingNameDao.findAll(offenderCallingNameExample);
    }
}
