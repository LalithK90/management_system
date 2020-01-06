package lk.imms.management_system.asset.offender.dao;

import lk.imms.management_system.asset.offender.entity.OffenderCallingName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffenderCallingNameDao extends JpaRepository< OffenderCallingName, Long > {
}
