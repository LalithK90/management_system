package lk.imms.management_system.asset.petitionAddOffender.dao;

import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PetitionOffenderDao extends JpaRepository< PetitionOffender, Long > {
    List< PetitionOffender> findByPetition(Petition petition);

    PetitionOffender findFirstByOrderByIdDesc();

    PetitionOffender findByPetitionAndOffender(Petition petition, Offender offender);

    List< PetitionOffender> findByOffender(Offender offender);

    List< PetitionOffender> countByOffenderAndCreatedAtBetween(Offender offender, LocalDateTime from, LocalDateTime to);

    List<PetitionOffender> findByContravenes(Contravene contravene);

    List<PetitionOffender> findByOffenderAndContravenes(Offender offender, Contravene contravene);
}
