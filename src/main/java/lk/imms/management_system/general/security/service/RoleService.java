package lk.imms.management_system.general.security.service;

import lk.imms.management_system.general.security.dao.RoleDao;
import lk.imms.management_system.general.security.entity.Role;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements AbstractService<Role, Long> {
    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }



    public List<Role> findAll() {
        return roleDao.findAll();
    }


    public Role findById(Long id) {
        return roleDao.getOne(id);
    }


    public Role persist(Role role) {
        return roleDao.save(role);
    }


    public boolean delete(Long id) {
        roleDao.deleteById(id);
        return false;
    }


    public List<Role> search(Role role) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Role> roleExample = Example.of(role, matcher);
        return roleDao.findAll(roleExample);
    }
}
