package lk.imms.management_system.asset.offenders.dao;

import lk.imms.management_system.asset.offenders.entity.OffenderDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffenderDescriptionDao extends JpaRepository< OffenderDescription, Long > {
}
