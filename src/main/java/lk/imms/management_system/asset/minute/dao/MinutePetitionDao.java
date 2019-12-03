package lk.imms.management_system.asset.minute.dao;

import lk.imms.management_system.asset.minute.entity.MinutePetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinutePetitionDao extends JpaRepository< MinutePetition, Long > {
}
