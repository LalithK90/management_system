package lk.imms.management_system.asset.employee.service;

import lk.imms.management_system.asset.employee.dao.EmployeeFilesDao;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.EmployeeFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeFilesService {
    private final EmployeeFilesDao employeeFilesDao;

    @Autowired
    public EmployeeFilesService(EmployeeFilesDao employeeFilesDao) {
        this.employeeFilesDao = employeeFilesDao;
    }

    public List< EmployeeFiles > findByEmployee(Employee employee) {
        //return employeeFilesDao.findByEmployee(employee);
        return employeeFilesDao.findByEmployeeOrderByIdDesc(employee);
    }

    public EmployeeFiles findByName(String filename) {
        return   employeeFilesDao.findByName(filename);
    }

    public void persist(List< EmployeeFiles > storedFile) {
        employeeFilesDao.saveAll(storedFile);
    }

    public List< EmployeeFiles > search(EmployeeFiles employeeFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< EmployeeFiles > employeeFilesExample = Example.of(employeeFiles, matcher);
        return employeeFilesDao.findAll(employeeFilesExample);
    }

    public EmployeeFiles findById(Long id) {
        return employeeFilesDao.getOne(id);
    }

    public EmployeeFiles findByNewName(String filename) {
        return employeeFilesDao.findByNewName(filename);
    }

    public EmployeeFiles findByNewID(String filename) {
        return employeeFilesDao.findByNewId(filename);
    }
}
