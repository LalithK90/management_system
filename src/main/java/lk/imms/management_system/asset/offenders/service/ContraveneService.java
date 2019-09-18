package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.ContraveneDao;
import lk.imms.management_system.asset.offenders.entity.Contravene;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContraveneService implements AbstractService< Contravene, Long > {
    private final ContraveneDao contraveneDao;

    @Autowired
    public ContraveneService(ContraveneDao contraveneDao) {
        this.contraveneDao = contraveneDao;
    }

    @Override
    public List< Contravene > findAll() {
        return contraveneDao.findAll();
    }

    @Override
    public Contravene findById(Long id) {
        return contraveneDao.getOne(id);
    }

    @Override
    public Contravene persist(Contravene contravene) {
       //todo-> plz check what are the fact need to check before save
        return contraveneDao.save(contravene);
    }

    @Override
    public boolean delete(Long id) {
        //there is no possibilities to delete
        contraveneDao.deleteById(id);
        return true;
    }

    @Override
    public List< Contravene > search(Contravene contravene) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Contravene > contravenes = Example.of(contravene, matcher);
        return contraveneDao.findAll(contravenes);
    }
}
