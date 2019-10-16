package lk.imms.management_system.asset.commonAsset.service;

import lk.imms.management_system.asset.commonAsset.dao.WorkingPlaceDao;
import lk.imms.management_system.asset.commonAsset.entity.WorkingPlace;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
        return workingPlaceDao.getOne(id);
    }

    @Override
    public WorkingPlace persist(WorkingPlace workingPlace) {
        return workingPlaceDao.save(workingPlace);
    }

    @Override
    public boolean delete(Long id) {
        workingPlaceDao.deleteById(id);
        return true;
    }

    @Override
    public List< WorkingPlace > search(WorkingPlace workingPlace) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< WorkingPlace > workingPlaceExample = Example.of(workingPlace, matcher);
        return workingPlaceDao.findAll(workingPlaceExample);
    }
}
