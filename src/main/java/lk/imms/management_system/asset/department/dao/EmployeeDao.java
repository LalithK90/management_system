package lk.imms.management_system.asset.department.dao;

import lk.imms.management_system.asset.department.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    Employee findFirstByOrderByIdDesc();

}
