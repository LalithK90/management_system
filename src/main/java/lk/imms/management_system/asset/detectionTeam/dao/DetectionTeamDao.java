package lk.imms.management_system.asset.detectionTeam.dao;

import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.petition.entity.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectionTeamDao extends JpaRepository< DetectionTeam, Long > {
    DetectionTeam findFirstByOrderByIdDesc();

    List< DetectionTeam> findByPetition(Petition petition);
}
