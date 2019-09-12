package lk.imms.management_system.asset.detection.dao;

import lk.imms.management_system.asset.detection.entity.DetectionTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionTeamDao extends JpaRepository< DetectionTeam, Long > {
}
