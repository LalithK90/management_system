package lk.imms.management_system.asset.court.dao;

import lk.imms.management_system.asset.court.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtDao extends JpaRepository< Court, Long > {
}
