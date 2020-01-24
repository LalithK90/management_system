package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.Enum.PetitionStateType;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface PetitionStatuDao extends JpaRepository< PetitionState , Long > {
    List<PetitionState> findByPetition(Petition petition);

    List< PetitionState> findByPetitionStateTypeAndCreatedAtBetween(PetitionStateType petitionStateType, LocalDateTime from, LocalDateTime to);
}
