package lk.imms.management_system.asset.crime.dao;

import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrimeDao extends JpaRepository< Crime, Long> {
    List< Crime> findByDetectionTeam(DetectionTeam detectionTeam);
}
