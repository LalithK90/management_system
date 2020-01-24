package lk.imms.management_system.asset.crime.dao;

import lk.imms.management_system.asset.crime.entity.Crime;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.userManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CrimeDao extends JpaRepository< Crime, Long > {
    List< Crime > findByDetectionTeam(DetectionTeam detectionTeam);

    List< Crime > findAllByOrderByIdDesc();

    List< Crime > findByCreatedByAndCreatedAtBetween(User user, LocalDateTime from, LocalDateTime to);
}
