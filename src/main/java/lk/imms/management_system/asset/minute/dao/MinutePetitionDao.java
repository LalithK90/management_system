package lk.imms.management_system.asset.minute.dao;

import lk.imms.management_system.asset.minute.entity.MinutePetition;
import lk.imms.management_system.asset.petition.entity.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinutePetitionDao extends JpaRepository< MinutePetition, Long > {

    List< MinutePetition > findByPetition(Petition petition);
}
