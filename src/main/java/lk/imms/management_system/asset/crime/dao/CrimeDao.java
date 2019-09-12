package lk.imms.management_system.asset.crime.dao;

import lk.imms.management_system.asset.crime.entity.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeDao extends JpaRepository< Crime, Long> {
}
