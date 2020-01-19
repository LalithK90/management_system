package lk.imms.management_system.asset.employee.dao;

import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.EmployeeWorkingPlaceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeWorkingPlaceHistoryDao extends JpaRepository< EmployeeWorkingPlaceHistory, Long > {
       List< EmployeeWorkingPlaceHistory > findByEmployee(Employee employee);
}
