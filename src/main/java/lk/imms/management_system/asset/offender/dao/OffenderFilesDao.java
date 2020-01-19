package lk.imms.management_system.asset.offender.dao;

import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.entity.OffenderFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffenderFilesDao extends JpaRepository< OffenderFiles, Long > {

    List< OffenderFiles > findByOffenderOrderByIdDesc(Offender offender);

    OffenderFiles findByName(String filename);

    OffenderFiles findByNameAndOffender(String fileName, Offender offender);

    OffenderFiles findByNewName(String filename);

    OffenderFiles findByNewId(String filename);
}
