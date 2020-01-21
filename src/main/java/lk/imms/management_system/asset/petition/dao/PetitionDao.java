package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PetitionDao extends JpaRepository< Petition, Long > {
    Petition findFirstByOrderByIdDesc();

    List< Petition > findAllByOrderByIdDesc();

    Long countByWorkingPlaceAndCreatedAtBetween(WorkingPlace workingPlace, LocalDateTime from, LocalDateTime to);
}
/*SELECT YEAR(date_field) as stat_year,
       MONTH(date_field) as stat_month,
       COUNT(*) as stat_count
FROM `table`
GROUP BY stat_year,stat_month

**+**data group to year month
*/