package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.OffenderDao;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffenderService implements AbstractService< Offender, Long> {
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
        return null;
    }

    @Override
    public Offender persist(Offender offender) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< Offender > search(Offender offender) {
        return null;
    }
}
