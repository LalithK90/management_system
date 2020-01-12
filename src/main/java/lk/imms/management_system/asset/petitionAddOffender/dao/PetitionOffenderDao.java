package lk.imms.management_system.asset.petitionAddOffender.dao;

import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionOffenderDao extends JpaRepository< PetitionOffender, Long > {
}
