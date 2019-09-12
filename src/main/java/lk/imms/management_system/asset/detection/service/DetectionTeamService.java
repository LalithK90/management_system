package lk.imms.management_system.asset.detection.service;

import lk.imms.management_system.asset.detection.dao.DetectionTeamDao;
import lk.imms.management_system.asset.detection.entity.DetectionTeam;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectionTeamService implements AbstractService< DetectionTeam, Long > {
    private final DetectionTeamDao detectionTeamDao;

    public DetectionTeamService(DetectionTeamDao detectionTeamDao) {
        this.detectionTeamDao = detectionTeamDao;
    }

    @Override
    public List< DetectionTeam > findAll() {
        return detectionTeamDao.findAll();
    }

    @Override
    public DetectionTeam findById(Long id) {
        return null;
    }

    @Override
    public DetectionTeam persist(DetectionTeam detectionTeam) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< DetectionTeam > search(DetectionTeam detectionTeam) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< DetectionTeam > detectionTeamExample = Example.of(detectionTeam, matcher);
        return detectionTeamDao.findAll(detectionTeamExample);
    }
}
