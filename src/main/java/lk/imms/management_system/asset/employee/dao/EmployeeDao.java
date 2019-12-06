package lk.imms.management_system.asset.employee.dao;

import lk.imms.management_system.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    Employee findFirstByOrderByIdDesc();


}
