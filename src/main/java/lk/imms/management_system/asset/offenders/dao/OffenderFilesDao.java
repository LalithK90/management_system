package lk.imms.management_system.asset.offenders.dao;

import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.asset.offenders.entity.OffenderFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffenderFilesDao extends JpaRepository< OffenderFiles, Long > {

    List< OffenderFiles > findByOffenderOrderByIdDesc(Offender offender);

    OffenderFiles findByName(String filename);

    OffenderFiles findByNewName(String filename);

    OffenderFiles findByNewId(String filename);
}
