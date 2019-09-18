package lk.imms.management_system.asset.minute.service;

import lk.imms.management_system.asset.minute.dao.MinuteDao;
import lk.imms.management_system.asset.minute.entity.Minute;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinuteService implements AbstractService< Minute, Long > {
    private final MinuteDao minuteDao;
@Autowired
    public MinuteService(MinuteDao minuteDao) {
        this.minuteDao = minuteDao;
    }

    @Override
    public List< Minute > findAll() {
        return minuteDao.findAll();
    }

    @Override
    public Minute findById(Long id) {
        return minuteDao.getOne(id);
    }

    @Override
    public Minute persist(Minute minute) {
    //todo -> find what are the things to check before save
    return minuteDao.save(minute);
    }

    @Override
    public boolean delete(Long id) {
    // there is no possibility to delete any minute
    return false;
    }

    @Override
    public List< Minute > search(Minute minute) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Minute > minuteExample = Example.of(minute, matcher);
        return minuteDao.findAll(minuteExample);
    }
}
