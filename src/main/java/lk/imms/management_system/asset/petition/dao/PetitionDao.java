package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionDao extends JpaRepository<Petition, Long > {
    Petition findFirstByOrderByIdDesc();
}
