package lk.imms.management_system.asset.userManagement.service;


import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.userManagement.dao.UserDao;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@CacheConfig( cacheNames = {"user"} ) // tells Spring where to store cache for this class
public class UserService implements AbstractService< User, Long > {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Cacheable("user")
    public List< User > findAll() {
        return userDao.findAll();
    }

    public User findById(Long id) {
        return userDao.getOne(id);
    }

    @CachePut("user")
    public User persist(User user) {
        user.setEnabled(true);
        user.setUsername(user.getUsername().toLowerCase());
        if ( user.getPassword() != null ) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        } else {
            user.setPassword(userDao.getOne(user.getId()).getPassword());
        }
        return userDao.save(user);
    }

    public boolean delete(Long id) {
        userDao.deleteById(id);
        return false;
    }


    public List< User > search(User user) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< User > userExample = Example.of(user, matcher);
        return userDao.findAll(userExample);
    }

    public Long findByEmployeeId(Long id) {
        return userDao.findByEmployeeId(id);
    }

    public Long findByUserIdByUserName(String userName) {
        return userDao.findUserIdByUserName(userName);
    }

    public User findByUserName(String name) {
        return userDao.findByUsername(name);
    }

    public User findUserByEmployee(Employee employee) {
        return userDao.findByEmployee(employee);
    }
}