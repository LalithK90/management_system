package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.ContraveneDao;
import lk.imms.management_system.asset.offenders.entity.Contravene;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
    }

    @Override
    public Contravene persist(Contravene contravene) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< Contravene > search(Contravene contravene) {
        return null;
    }
}
