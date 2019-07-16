package lk.imms.management_system.resources.dao;

import lk.imms.management_system.resources.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    Employee findFirstByOrderByIdDesc();

}
