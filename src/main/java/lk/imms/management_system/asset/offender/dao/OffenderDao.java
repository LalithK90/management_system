package lk.imms.management_system.asset.offender.dao;

import lk.imms.management_system.asset.offender.entity.Offender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OffenderDao extends JpaRepository< Offender, Long > {
    Offender findFirstByOrderByIdDesc();

    Offender findByOffenderRegisterNumber(String offenderRegisterNumber);

    List< Offender > findByNameSinhala(String nameSinhala);

    List< Offender > findByNameTamil(String nameTamil);

    List< Offender > findByNameEnglish(String nameEnglish);

    Offender findByNic(String nic);

    Offender findByPassportNumber(String passportNumber);

    Offender findByDrivingLicenceNumber(String drivingLicenceNumber);

    List< Offender > findByMobileOne(String mobileOne);

    List< Offender > findByMobileTwo(String mobileTwo);

    List< Offender > findByLand(String land);

    Offender findByEmail(String email);

    List< Offender > findByDescription(String description);

    List<Offender> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
