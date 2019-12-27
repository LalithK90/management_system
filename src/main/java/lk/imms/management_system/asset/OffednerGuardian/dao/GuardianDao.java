package lk.imms.management_system.asset.OffednerGuardian.dao;

import lk.imms.management_system.asset.OffednerGuardian.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianDao extends JpaRepository< Guardian, Long > {
}
