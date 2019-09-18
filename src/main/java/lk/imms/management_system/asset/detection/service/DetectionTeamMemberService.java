package lk.imms.management_system.asset.detection.service;

import lk.imms.management_system.asset.detection.dao.DetectionTeamMemberDao;
import lk.imms.management_system.asset.detection.entity.DetectionTeamMember;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectionTeamMemberService implements AbstractService< DetectionTeamMember, Long > {
    private final DetectionTeamMemberDao detectionTeamMemberDao;
@Autowired
    public DetectionTeamMemberService(DetectionTeamMemberDao detectionTeamMemberDao) {
        this.detectionTeamMemberDao = detectionTeamMemberDao;
    }

    @Override
    public List< DetectionTeamMember > findAll() {
        return detectionTeamMemberDao.findAll();
    }

    @Override
    public DetectionTeamMember findById(Long id) {
        return detectionTeamMemberDao.getOne(id);
    }

    @Override
    public DetectionTeamMember persist(DetectionTeamMember detectionTeamMember) {
    //todo -> detection team creation what are the required things to check
    return detectionTeamMemberDao.save(detectionTeamMember);
    }

    @Override
    public boolean delete(Long id) {
    //todo -> need to find fact before delete this
    detectionTeamMemberDao.deleteById(id);
    return true;
    }

    @Override
    public List< DetectionTeamMember > search(DetectionTeamMember detectionTeamMember) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< DetectionTeamMember > detectionTeamMemberExample = Example.of(detectionTeamMember, matcher);
        return detectionTeamMemberDao.findAll(detectionTeamMemberExample);
    }
}
