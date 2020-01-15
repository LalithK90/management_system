package lk.imms.management_system.asset.employee.service;

import lk.imms.management_system.asset.commonAsset.entity.FileInfo;
import lk.imms.management_system.asset.employee.controller.EmployeeController;
import lk.imms.management_system.asset.employee.dao.EmployeeFilesDao;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.entity.EmployeeFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "employeeFiles")
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
        return employeeFilesDao.findByName(filename);
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

    @Cacheable
    public List< FileInfo > employeeFileDownloadLinks(Employee employee) {
        return employeeFilesDao.findByEmployeeOrderByIdDesc(employee)
                .stream()
                .map(employeeFiles -> {
                    String filename = employeeFiles.getName();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(EmployeeController.class, "downloadFile", employeeFiles.getNewId())
                            .build()
                            .toString();
                    return new FileInfo(filename, employeeFiles.getCreatedAt(), url);
                })
                .collect(Collectors.toList());
    }
}
