package lk.imms.management_system.asset.offenders.dao;

import lk.imms.management_system.asset.offenders.entity.Offender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffenderDao extends JpaRepository<Offender, Long > {
    Offender findFirstByOrderByIdDesc();
}
