package lk.imms.management_system.asset.userManagement.dao;


import lk.imms.management_system.asset.userManagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface RoleDao extends JpaRepository< Role, Long> {
}
