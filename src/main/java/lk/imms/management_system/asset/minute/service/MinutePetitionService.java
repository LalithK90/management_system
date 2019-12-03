package lk.imms.management_system.asset.minute.service;


import lk.imms.management_system.asset.minute.dao.MinutePetitionDao;
import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinutePetitionService implements AbstractService< MinutePetition, Long > {
    private final MinutePetitionDao minutePetitionDao;

    @Autowired
    public MinutePetitionService(MinutePetitionDao minutePetitionDao) {
        this.minutePetitionDao = minutePetitionDao;
    }

    @Override
    public List< MinutePetition > findAll() {
        return minutePetitionDao.findAll();
    }

    @Override
    public MinutePetition findById(Long id) {
        return minutePetitionDao.getOne(id);
    }

    @Override
    public MinutePetition persist(MinutePetition minutePetition) {
        //todo -> find what are the things to check before save
        return minutePetitionDao.save(minutePetition);
    }

    @Override
    public boolean delete(Long id) {
        // there is no possibility to delete any minutePetition
        return false;
    }

    @Override
    public List< MinutePetition > search(MinutePetition minutePetition) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< MinutePetition > minuteExample = Example.of(minutePetition, matcher);
        return minutePetitionDao.findAll(minuteExample);
    }
}
