package lk.imms.management_system.asset.commonAsset.dao;

import lk.imms.management_system.asset.commonAsset.entity.WorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingPlaceDao extends JpaRepository<WorkingPlace, Long> {
}
