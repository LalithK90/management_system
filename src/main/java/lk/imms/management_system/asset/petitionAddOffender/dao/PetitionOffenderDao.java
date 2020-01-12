package lk.imms.management_system.asset.petitionAddOffender.dao;

import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionOffenderDao extends JpaRepository< PetitionOffender, Long > {
    List< PetitionOffender> findByPetition(Petition petition);

    PetitionOffender findFirstByOrderByIdDesc();

    PetitionOffender findByPetitionAndOffender(Petition petition, Offender offender);

}
