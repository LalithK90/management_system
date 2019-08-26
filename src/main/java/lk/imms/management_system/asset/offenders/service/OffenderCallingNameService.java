package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.OffenderCallingNameDao;
import lk.imms.management_system.asset.offenders.entity.OffenderCallingName;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
    }

    @Override
    public OffenderCallingName persist(OffenderCallingName offenderCallingName) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< OffenderCallingName > search(OffenderCallingName offenderCallingName) {
        return null;
    }
}
