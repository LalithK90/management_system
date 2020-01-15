package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionStatusDao extends JpaRepository< PetitionState , Long > {
    List<PetitionState> findByPetition(Petition petition);
}
