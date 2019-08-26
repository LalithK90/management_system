package lk.imms.management_system.asset.department.dao;

import lk.imms.management_system.asset.department.entity.WorkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingHistoryDao extends JpaRepository< WorkingHistory, Long > {
}
