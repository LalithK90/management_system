package lk.imms.management_system.asset.userManagement.service;


import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.userManagement.dao.UserDao;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig( cacheNames = {"user"} ) // tells Spring where to store cache for this class
public class UserService implements AbstractService< User, Long > {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Cacheable
    public List< User > findAll() {
        return userDao.findAll();
    }

    @Cacheable
    public User findById(Long id) {
        return userDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "user", allEntries = true )},
            put = {@CachePut( value = "user", key = "#user.id" )} )
    @Transactional
    public User persist(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        if ( user.getPassword() != null ) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDao.getOne(user.getId()).getPassword());
        }
        return userDao.save(user);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //according to this project can not be deleted user
        userDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< User > search(User user) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< User > userExample = Example.of(user, matcher);
        return userDao.findAll(userExample);
    }

    public Long findByEmployeeId(Long id) {
        return userDao.findByEmployeeId(id);
    }

    @Cacheable
    public Long findByUserIdByUserName(String userName) {
        return userDao.findUserIdByUserName(userName);
    }

    @Cacheable
    public User findByUserName(String name) {
        return userDao.findByUsername(name);
    }

    @Cacheable
    public User findUserByEmployee(Employee employee) {
        return userDao.findByEmployee(employee);
    }
}