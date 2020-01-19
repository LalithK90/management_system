package lk.imms.management_system.asset.detectionTeam.service;

import lk.imms.management_system.asset.detectionTeam.dao.DetectionTeamDao;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@CacheConfig( cacheNames = "detectionTeam" )
public class DetectionTeamService implements AbstractService< DetectionTeam, Long > {
    private final DetectionTeamDao detectionTeamDao;

    public DetectionTeamService(DetectionTeamDao detectionTeamDao) {
        this.detectionTeamDao = detectionTeamDao;
    }

    @Override
    @Cacheable
    public List< DetectionTeam > findAll() {
        return detectionTeamDao.findAll();
    }

    @Override
    @Cacheable
    public DetectionTeam findById(Long id) {
        return detectionTeamDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "detectionTeam", allEntries = true )},
            put = {@CachePut( value = "detectionTeam", key = "#detectionTeam.id" )} )
    public DetectionTeam persist(DetectionTeam detectionTeam) {

        return detectionTeamDao.save(detectionTeam);
    }

    @Override
    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        detectionTeamDao.deleteById(id);
        return true;
    }

    @Override
    @Cacheable
    public List< DetectionTeam > search(DetectionTeam detectionTeam) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< DetectionTeam > detectionTeamExample = Example.of(detectionTeam, matcher);
        return detectionTeamDao.findAll(detectionTeamExample);
    }

    @Cacheable
    public DetectionTeam getLastTeam() {
        return detectionTeamDao.findFirstByOrderByIdDesc();
    }

    @Cacheable
    public List< DetectionTeam > findByPetition(Petition petition) {
    return detectionTeamDao.findByPetition(petition);
    }
}
