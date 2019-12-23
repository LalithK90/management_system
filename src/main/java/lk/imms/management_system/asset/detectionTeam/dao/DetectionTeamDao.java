package lk.imms.management_system.asset.detectionTeam.dao;

import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionTeamDao extends JpaRepository< DetectionTeam, Long > {
    DetectionTeam findFirstByOrderByIdDesc();
}
