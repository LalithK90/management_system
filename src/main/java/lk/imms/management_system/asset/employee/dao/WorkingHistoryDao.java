package lk.imms.management_system.asset.employee.dao;

import lk.imms.management_system.asset.employee.entity.WorkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingHistoryDao extends JpaRepository< WorkingHistory, Long > {
}
