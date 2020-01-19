package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface PetitionDao extends JpaRepository<Petition, Long > {
    Petition findFirstByOrderByIdDesc();

    Long countByWorkingPlaceAndCreatedAtBetween(WorkingPlace workingPlace, LocalDateTime from, LocalDateTime to);
}
