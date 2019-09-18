package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.OffenderContactDetailDao;
import lk.imms.management_system.asset.offenders.entity.OffenderContactDetail;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffenderContactDetailService implements AbstractService< OffenderContactDetail, Long > {
    private final OffenderContactDetailDao offenderContactDetailDao;
@Autowired
    public OffenderContactDetailService(OffenderContactDetailDao offenderContactDetailDao) {
        this.offenderContactDetailDao = offenderContactDetailDao;
    }

    @Override
    public List< OffenderContactDetail > findAll() {
        return offenderContactDetailDao.findAll();
    }

    @Override
    public OffenderContactDetail findById(Long id) {
        return offenderContactDetailDao.getOne(id);
    }

    @Override
    public OffenderContactDetail persist(OffenderContactDetail offenderContactDetail) {
    //todo -> find fact before need to save
    return offenderContactDetailDao.save(offenderContactDetail);
    }

    @Override
    public boolean delete(Long id) {
    //there is no possibilities to delete any offender in the system
    offenderContactDetailDao.deleteById(id);
    return true;
    }

    @Override
    public List< OffenderContactDetail > search(OffenderContactDetail offenderContactDetail) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< OffenderContactDetail > offenderContactDetailExample = Example.of(offenderContactDetail, matcher);
        return offenderContactDetailDao.findAll(offenderContactDetailExample);
    }
}
