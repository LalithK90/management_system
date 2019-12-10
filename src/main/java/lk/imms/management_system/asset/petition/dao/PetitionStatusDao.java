package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.PetitionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionStatusDao extends JpaRepository< PetitionState , Long > {
}
