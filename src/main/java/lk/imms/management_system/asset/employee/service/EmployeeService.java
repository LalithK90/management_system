package lk.imms.management_system.asset.employee.service;

import lk.imms.management_system.asset.employee.dao.EmployeeDao;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional// spring transactional annotation need to tell spring to this method work through the project
public class EmployeeService implements AbstractService< Employee, Long > {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    public List< Employee > findAll() {
        return employeeDao.findAll();
    }


    public Employee findById(Long id) {
        return employeeDao.getOne(id);
    }


    public Employee persist(Employee employee) {
        return employeeDao.save(employee);
    }


    public boolean delete(Long id) {
        employeeDao.deleteById(id);
        return false;
    }

    public List< Employee > search(Employee employee) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Employee > employeeExample = Example.of(employee, matcher);
        return employeeDao.findAll(employeeExample);
    }

    public boolean isEmployeePresent(Employee employee) {
        return employeeDao.equals(employee);
    }


    public Employee lastEmployee() {
        return employeeDao.findFirstByOrderByIdDesc();
    }


}
