package lk.imms.management_system.asset.crime.dao;

import lk.imms.management_system.asset.crime.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtDao extends JpaRepository< Court, Long > {
}
