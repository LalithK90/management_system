package lk.imms.management_system.asset.employee.service;

import lk.imms.management_system.asset.commonAsset.service.CommonCodeService;
import lk.imms.management_system.asset.employee.dao.EmployeeDao;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig( cacheNames = "employee" )
public class EmployeeService implements AbstractService< Employee, Long > {

    private final EmployeeDao employeeDao;
    private final CommonCodeService commonCodeService;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao, CommonCodeService commonCodeService) {
        this.employeeDao = employeeDao;
        this.commonCodeService = commonCodeService;
    }

    @Cacheable
    public List< Employee > findAll() {
        return employeeDao.findAll();
    }

    @Cacheable
    public Employee findById(Long id) {
        return employeeDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "employee", allEntries = true )},
            put = {@CachePut( value = "employee", key = "#employee.id" )} )
    public Employee persist(Employee employee) {
        employee.setMobileOne(commonCodeService.commonMobileNumberLengthValidator(employee.getMobileOne()));
        employee.setMobileTwo(commonCodeService.commonMobileNumberLengthValidator(employee.getMobileTwo()));
        employee.setLand(commonCodeService.commonMobileNumberLengthValidator(employee.getLand()));

        return employeeDao.save(employee);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        employeeDao.deleteById(id);
        return false;
    }

    @Cacheable
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


    public List< Employee > findByWorkingPlace(WorkingPlace workingPlace) {
        return employeeDao.findByWorkingPlace(workingPlace);
    }

    @Cacheable
    public Employee findByNic(String nic) {
        return employeeDao.findByNic(nic);
    }
}
