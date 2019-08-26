package lk.imms.management_system.asset.commonAsset.service;

import lk.imms.management_system.asset.commonAsset.dao.WorkingPlaceDao;
import lk.imms.management_system.asset.commonAsset.entity.WorkingPlace;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingPlaceService implements AbstractService< WorkingPlace, Long > {
    private final WorkingPlaceDao workingPlaceDao;

    @Autowired
    public WorkingPlaceService(WorkingPlaceDao workingPlaceDao) {
        this.workingPlaceDao = workingPlaceDao;
    }


    @Override
    public List< WorkingPlace > findAll() {
        return workingPlaceDao.findAll();
    }

    @Override
    public WorkingPlace findById(Long id) {
        return null;
    }

    @Override
    public WorkingPlace persist(WorkingPlace workingPlace) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< WorkingPlace > search(WorkingPlace workingPlace) {
        return null;
    }
}
