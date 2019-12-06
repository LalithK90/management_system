package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.Petitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionerDao extends JpaRepository< Petitioner, Long > {
}
