package lk.imms.management_system.asset.userManagement.service;


import lk.imms.management_system.asset.userManagement.dao.RoleDao;
import lk.imms.management_system.asset.userManagement.entity.Role;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@CacheConfig( cacheNames = {"role"} ) // tells Spring where to store cache for this class
public class RoleService implements AbstractService< Role, Long > {
    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Cacheable
    public List< Role > findAll() {
        return roleDao.findAll();
    }


    public Role findById(Long id) {
        return roleDao.getOne(id);
    }

    @CachePut("role")
    public Role persist(Role role) {
        role.setRoleName(role.getRoleName().toUpperCase());
        return roleDao.save(role);
    }


    public boolean delete(Long id) {
        roleDao.deleteById(id);
        return true;
    }


    public List< Role > search(Role role) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Role > roleExample = Example.of(role, matcher);
        return roleDao.findAll(roleExample);
    }
}
