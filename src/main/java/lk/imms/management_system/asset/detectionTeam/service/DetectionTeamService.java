package lk.imms.management_system.asset.detectionTeam.service;

import com.itextpdf.text.pdf.PdfIndirectReference;
import lk.imms.management_system.asset.detectionTeam.dao.DetectionTeamDao;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
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
        return detectionTeamDao.getOne(id);
    }

    @Override
    public DetectionTeam persist(DetectionTeam detectionTeam)
    { return detectionTeamDao.save(detectionTeam);
    }

    @Override
    public boolean delete(Long id) {
       detectionTeamDao.deleteById(id);
        return true;
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


    public DetectionTeam getLastTeam() {
      return   detectionTeamDao.findFirstByOrderByIdDesc();
    }
}
