package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.OffenderDao;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffenderService implements AbstractService< Offender, Long > {
    private final OffenderDao offenderDao;

    @Autowired
    public OffenderService(OffenderDao offenderDao) {
        this.offenderDao = offenderDao;
    }


    @Override
    public List< Offender > findAll() {
        return offenderDao.findAll();
    }

    @Override
    public Offender findById(Long id) {
        return offenderDao.getOne(id);
    }

    @Override
    public Offender persist(Offender offender) {
        //todo -> need to find fact to before save
        return offenderDao.save(offender);
    }

    @Override
    public boolean delete(Long id) {
        //there is no possibilities to delete offender from system
        //more than 100 years need to save the in the system
        offenderDao.deleteById(id);
        return true;
    }

    @Override
    public List< Offender > search(Offender offender) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Offender > offenderExample = Example.of(offender, matcher);
        return offenderDao.findAll(offenderExample);
    }
}
