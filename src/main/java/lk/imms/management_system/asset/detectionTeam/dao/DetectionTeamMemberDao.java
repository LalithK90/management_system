package lk.imms.management_system.asset.detectionTeam.dao;

import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeam;
import lk.imms.management_system.asset.detectionTeam.entity.DetectionTeamMember;
import lk.imms.management_system.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetectionTeamMemberDao extends JpaRepository< DetectionTeamMember, Long > {
    List< DetectionTeam> findByEmployeeAndCreatedAtBetween(Employee employee, LocalDateTime from, LocalDateTime to);
}
