package lk.imms.management_system.asset.employee.service;

import lk.imms.management_system.asset.employee.dao.EmployeeWorkingPlaceHistoryDao;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.EmployeeWorkingPlaceHistory;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeWorkingPlaceHistoryService implements AbstractService< EmployeeWorkingPlaceHistory, Long > {
    private final EmployeeWorkingPlaceHistoryDao employeeWorkingPlaceHistoryDao;

    @Autowired
    public EmployeeWorkingPlaceHistoryService(EmployeeWorkingPlaceHistoryDao employeeWorkingPlaceHistoryDao) {
        this.employeeWorkingPlaceHistoryDao = employeeWorkingPlaceHistoryDao;
    }

    @Override
    public List< EmployeeWorkingPlaceHistory > findAll() {
        return employeeWorkingPlaceHistoryDao.findAll();
    }

    @Override
    public EmployeeWorkingPlaceHistory findById(Long id) {
        return employeeWorkingPlaceHistoryDao.getOne(id);
    }

    @Override
    public EmployeeWorkingPlaceHistory persist(EmployeeWorkingPlaceHistory employeeWorkingPlaceHistory) {
        return employeeWorkingPlaceHistoryDao.save(employeeWorkingPlaceHistory);
    }

    @Override
    public boolean delete(Long id) {
        //todo -> delete is a not a function in this system
        return false;
    }

    @Override
    public List< EmployeeWorkingPlaceHistory > search(EmployeeWorkingPlaceHistory employeeWorkingPlaceHistory) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< EmployeeWorkingPlaceHistory > workingHistoryExample = Example.of(employeeWorkingPlaceHistory, matcher);
        return employeeWorkingPlaceHistoryDao.findAll(workingHistoryExample);
    }

    public List< EmployeeWorkingPlaceHistory > findByEmployee(Employee employee) {
        return employeeWorkingPlaceHistoryDao.findByEmployee(employee);
    }
}
